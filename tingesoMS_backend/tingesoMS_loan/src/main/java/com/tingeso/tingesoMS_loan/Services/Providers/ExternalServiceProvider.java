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
    private RestTemplate restTemplate; // CORRECCIÓN: Cambiado de RestClientConfig a RestTemplate

    // --- CONSTANTES DE URL BASE ---
    private final String CLIENT_URL = "http://tingesoMS_client/api/client/";
    private final String INVENTORY_URL = "http://tingesoMS_inventory/api/inventory/";
    private final String TOOL_URL = "http://tingesoMS_inventory/api/inventory/";
    private final String FEE_URL = "http://tingesoMS_fee/api/fee/";
    private final String KARDEX_URL = "http://tingeso-kardex/api/kardex/";

    //private final String CLIENT_URL = "http://localhost:6002/api/client/";
    //private final String INVENTORY_URL = "http://localhost:6004/api/inventory/";
    //private final String TOOL_URL = "http://localhost:6004/api/inventory/";
    //private final String FEE_URL = "http://localhost:6003/api/fee/";
    //private final String KARDEX_URL = "http://localhost:6005/api/kardex/";

    // --- CLIENT SERVICE ---

    public ClientDto getClientByRut(String rut) {
        try {
            return restTemplate.getForObject(CLIENT_URL + "byRut?rut=" + rut, ClientDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateClientStatus(Long id) {
        try {
            restTemplate.put(CLIENT_URL + "updateStatus/" + id, null);
        } catch (Exception e) {
            System.err.println("Error updateClientStatus: " + e.getMessage());
        }
    }

    public List<ClientDto> getDelayedClientsDetails() {
        List<Long> delayedIds = loanRepo.findDelayedClientIds();
        if (delayedIds.isEmpty()) return Collections.emptyList();

        try {
            ClientDto[] clients = restTemplate.postForObject(CLIENT_URL + "getByIds", delayedIds, ClientDto[].class);
            return Arrays.asList(clients);
        } catch (Exception e) {
            System.err.println("Error al obtener detalles de clientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // --- INVENTORY SERVICE ---

    public ToolDto getToolById(Long id) {
        try {
            return restTemplate.getForObject(TOOL_URL + id, ToolDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateToolStatus(ToolDto statusDto) {
        try {
            restTemplate.put(INVENTORY_URL + "updateStatus", statusDto);
        } catch (Exception e) {
            System.err.println("Error updateToolStatus: " + e.getMessage());
        }
    }

    public void updateToolDamageStatus(ToolDto statusDto) {
        try {
            restTemplate.put(INVENTORY_URL + "underRepair", statusDto);
        } catch (Exception e) {
            System.err.println("Error updateToolDamageStatus: " + e.getMessage());
        }
    }

    public void returnLoanDeleteTool(ToolDto statusDto) {
        try {
            restTemplate.put(INVENTORY_URL + "deleteTool", statusDto);
        } catch (Exception e) {
            System.err.println("Error returnLoanDeleteTool: " + e.getMessage());
        }
    }

    public int countAvailable(String name, String category, Integer loanFee) {
        try {
            String url = INVENTORY_URL + "countAvailable?name=" + name + "&category=" + category + "&loanFee=" + loanFee;
            Integer count = restTemplate.getForObject(url, Integer.class);
            return count != null ? count : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    // --- FEE SERVICE ---

    public ToolFeeDto getToolFee(Long toolId) {
        try {
            return restTemplate.getForObject(FEE_URL + "tool/" + toolId, ToolFeeDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    // --- KARDEX SERVICE ---

    public void logCardex(CardexDto cardexDto) {
        try {
            // Ajustado para seguir el formato KARDEX_URL
            restTemplate.postForObject(KARDEX_URL + "log", cardexDto, Void.class);
        } catch (Exception e) {
            System.err.println("Error logCardex: " + e.getMessage());
        }
    }

    public void notifyKardexLoan(DtoLoan loan) {
        restTemplate.postForObject(KARDEX_URL + "saveLoan", loan, Void.class);
    }

    public void notifyKardexReturnLoan(ReturnLoanDto loanDto) {
        restTemplate.postForObject(KARDEX_URL + "saveReturnLoan", loanDto, Void.class);
    }

    public void notifyKardexReturnLoanDamage(ReturnLoanDto loanDto) {
        restTemplate.postForObject(KARDEX_URL + "saveReturnLoanDamage", loanDto, Void.class);
    }

    public void notifyKardexReturnLoanDelete(ReturnLoanDto loanDto) {
        restTemplate.postForObject(KARDEX_URL + "saveReturnLoanDelete", loanDto, Void.class);
    }

    // --- LÓGICA DE NEGOCIO ---

}