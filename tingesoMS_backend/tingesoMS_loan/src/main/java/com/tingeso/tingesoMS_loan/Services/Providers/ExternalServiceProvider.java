package com.tingeso.tingesoMS_loan.Services.Providers;

import com.tingeso.tingesoMS_loan.Dtos.*;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import com.tingeso.tingesoMS_loan.Repository.LoanRepositorie;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalServiceProvider {

    @Autowired
    private LoanRepositorie loanRepo;

    @Autowired
    private RestTemplate restTemplate;

    public ClientDto getClientByRut(String rut) {
        try {
            return restTemplate.getForObject("http://tingesoMS_client/api/client/byRut?rut=" + rut, ClientDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateClientStatus(Long id) {
        try {
            restTemplate.put("http://tingesoMS_client/api/client/updateStatus/" + id, null);
        } catch (Exception e) {
            // handle error
        }
    }

    public ToolDto getToolById(Long id) {
        try {
            return restTemplate.getForObject("http://tingesoMS_inventory/api/tool/" + id, ToolDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateToolStatus(ToolDto statusDto) {
        try {
            restTemplate.put("http://tingesoMS_inventory/api/inventory/updateStatus", statusDto);
        } catch (Exception e) {
            // handle error
        }
    }

    public void updateToolDamageStatus(ToolDto statusDto) {
        try {
            restTemplate.put("http://tingesoMS_inventory/api/inventory/underRepair", statusDto);
        } catch (Exception e) {
            // handle error
        }
    }

    public void returnLoanDeleteTool(ToolDto statusDto) {
        try {
            restTemplate.put("http://tingesoMS_inventory/api/inventory/deleteTool", statusDto);
        } catch (Exception e) {
            // handle error
        }
    }

    public ToolFeeDto getToolFee(Long toolId) {
        try {
            return restTemplate.getForObject("http://tingesoMS_fee/api/fee/tool/" + toolId, ToolFeeDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void logCardex(CardexDto cardexDto) {
        try {
            restTemplate.postForObject("http://tingesoMS_kardex/api/cardex/log", cardexDto, Void.class);
        } catch (Exception e) {
            // handle error
        }
    }
    
    public int countAvailable(String name, String category, Integer loanFee) {
        try {
            Integer count = restTemplate.getForObject("http://tingesoMS_inventory/api/inventory/countAvailable?name=" + name + "&category=" + category + "&loanFee=" + loanFee, Integer.class);
            return count != null ? count : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<ClientDto> getDelayedClientsDetails() {
        // 1. Obtenemos solo los IDs de los deudores desde la DB local de Loans
        List<Long> delayedIds = loanRepo.findDelayedClientIds();

        if (delayedIds.isEmpty()) return Collections.emptyList();

        // 2. Consultamos al microservicio de clientes por los detalles de esos IDs
        String url = "http://tingeso-client/api/clients/getByIds";

        try {
            // Enviamos la lista de IDs en el cuerpo del POST
            ClientDto[] clients = restTemplate.postForObject(url, delayedIds, ClientDto[].class);
            return Arrays.asList(clients);
        } catch (Exception e) {
            System.err.println("Error al obtener detalles de clientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional // Importante: si algo falla, no queremos guardar datos inconsistentes
    public Loan createLoan(String clientRut, Long toolId, LocalDate deliveryDate, LocalDate expectedReturnDate) {

        // 1. Validaciones básicas
        if (clientRut == null || toolId == null) {
            throw new IllegalArgumentException("clientRut y toolId no pueden ser nulos");
        }

        // 2. Obtener Cliente (Llamada remota a tingeso-auth o tingeso-client)
        ClientDto client = Optional.ofNullable(getClientByRut(clientRut))
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Long clientId = client.getIdCustomer();

        // 3. Obtener Herramienta (Llamada remota a tingeso-inventory)
        ToolDto tool = Optional.ofNullable(getToolById(toolId))
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada"));

        // 4. Validar disponibilidad de la herramienta (Lógica del DTO remoto)
        if (Boolean.FALSE.equals(tool.getStatus()) ||
                Boolean.TRUE.equals(tool.getDeleteStatus()) ||
                Boolean.TRUE.equals(tool.getUnderRepair())) {
            throw new IllegalStateException("La herramienta no está disponible para préstamo");
        }

        // 5. RF2.5: Cliente con multas pendientes (Lógica local con clientId)
        // Cambiamos findByClient por findByClientId
        List<Loan> defaulter = loanRepo.findByClientIdAndLoanStatusTrueAndPenaltyTrue(clientId);
        if (!defaulter.isEmpty()) {
            throw new IllegalStateException("El cliente tiene multas pendientes");
        }

        // 6. RF2.2: Validar Stock (Llamada remota al Inventory MS)
        // En microservicios, el Inventory MS debería tener un endpoint de /countAvailable
        int stock = countAvailable(tool.getName(), tool.getCategory(), tool.getLoanFee());
        if (stock <= 0) {
            throw new IllegalStateException("No hay stock disponible para: " + tool.getName());
        }

        // 7. RF: Máximo 5 préstamos activos (Lógica local con clientId)
        List<Loan> activeLoans = loanRepo.findByClientIdAndLoanStatusTrue(clientId);
        if (activeLoans.size() >= 5) {
            throw new IllegalStateException("El cliente ha alcanzado el máximo de 5 préstamos activos");
        }

        // 8. Crear el Préstamo (Guardamos IDs, no objetos)
        Loan loan = new Loan();
        loan.setClientId(clientId); // Guardamos el ID del cliente
        loan.setToolId(toolId);     // Guardamos el ID de la herramienta
        loan.setDeliveryDate(deliveryDate);
        loan.setReturnDate(expectedReturnDate);
        loan.setLoanStatus(true);
        loan.setPenalty(false);
        loan.setPenaltyTotal(0);

        // 9. Actualizar estado de la herramienta (Llamada remota PUT a Inventory)
        // Notificamos al microservicio de inventario que la herramienta ya no está disponible
        updateToolStatus(tool);

        return loanRepo.save(loan);
    }

    /*------------------------------------------------------*/
    private final String KARDEX_URL = "http://tingeso-kardex/api/kardex/";

    /* 1. Notificar Creación de Préstamo */
    public void notifyKardexLoan(DtoLoan loan) {
        restTemplate.postForObject(KARDEX_URL + "saveLoan", loan, Void.class);
    }

    /* 2. Notificar Creación de Herramienta */
    public void notifyKardexTool(CreateToolDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveTool", request, Void.class);
    }

    /* 3. Notificar Creación de Cliente */
    public void notifyKardexClient(DtoClient request) {
        restTemplate.postForObject(KARDEX_URL + "saveClient", request, Void.class);
    }

    /* 4. Notificar Actualización de Herramienta */
    public void notifyKardexUpdateTool(CreateToolDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveUpdateTool", request, Void.class);
    }

    /* 5. Notificar Cambio de Estado de Herramienta */
    public void notifyKardexUpdateStatusTool(ToolStatusDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveUpdateStatusTool", request, Void.class);
    }

    /* 6. Notificar Reparación de Herramienta */
    public void notifyKardexRepairTool(ToolStatusDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveRepairTool", request, Void.class);
    }

    /* 7. Notificar Eliminación de Herramienta */
    public void notifyKardexDeleteTool(ToolStatusDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveDeleteTool", request, Void.class);
    }

    /* 8. Notificar Devolución de Préstamo (Normal) */
    public void notifyKardexReturnLoan(ReturnLoanDto loanDto) {
        restTemplate.postForObject(KARDEX_URL + "saveReturnLoan", loanDto, Void.class);
    }

    /* 9. Notificar Devolución con Daño */
    public void notifyKardexReturnLoanDamage(ReturnLoanDto loanDto) {
        restTemplate.postForObject(KARDEX_URL + "saveReturnLoanDamage", loanDto, Void.class);
    }

    /* 10. Notificar Devolución con Reposición (Delete) */
    public void notifyKardexReturnLoanDelete(ReturnLoanDto loanDto) {
        restTemplate.postForObject(KARDEX_URL + "saveReturnLoanDelete", loanDto, Void.class);
    }

}
