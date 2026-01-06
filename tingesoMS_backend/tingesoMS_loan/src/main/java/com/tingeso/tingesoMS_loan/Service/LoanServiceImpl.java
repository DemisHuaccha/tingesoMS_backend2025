package com.tingeso.tingesoMS_loan.Service;

import com.tingeso.tingesoMS_loan.Dtos.*;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import com.tingeso.tingesoMS_loan.Repository.LoanRepositorie;
import com.tingeso.tingesoMS_loan.Services.Providers.ExternalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanServiceImpl {

    @Autowired
    private LoanRepositorie loanRepo;
    
    @Autowired
    private ExternalServiceProvider externalService;

    public Boolean isToolAvailableForClient(Long clientId, String toolName, String toolCategory, Integer loanFee) {
        return loanRepo.clientHasNoMatchingLoan(clientId, toolName, toolCategory, loanFee);
    }

    @Transactional
    public Loan createLoan(String clientRut, Long toolId, LocalDate deliveryDate, LocalDate expectedReturnDate, String email) {
        if (clientRut == null || toolId == null) {
            throw new IllegalArgumentException("clientRut y toolId no pueden ser nulos");
        }

        ClientDto client = Optional.ofNullable(externalService.getClientByRut(clientRut))
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Long clientId = client.getIdCustomer();

        ToolDto tool = Optional.ofNullable(externalService.getToolById(toolId))
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada"));

        if (Boolean.FALSE.equals(tool.getStatus()) ||
                Boolean.TRUE.equals(tool.getDeleteStatus()) ||
                Boolean.TRUE.equals(tool.getUnderRepair())) {
            throw new IllegalStateException("La herramienta no está disponible para préstamo");
        }

        List<Loan> defaulter = loanRepo.findByClientIdAndLoanStatusTrueAndPenaltyTrue(clientId);
        if (!defaulter.isEmpty()) {
            throw new IllegalStateException("El cliente tiene multas pendientes");
        }

        int stock = externalService.countAvailable(tool.getName(), tool.getCategory(), tool.getLoanFee());
        if (stock <= 0) {
            throw new IllegalStateException("No hay stock disponible para: " + tool.getName());
        }

        if (loanRepo.existsActiveLoanWithSameTool(clientId, tool.getName(), tool.getCategory(), tool.getLoanFee())){
            throw new IllegalStateException("El cliente tiene un prestamo con la misma tool");
        }

        Loan loan = new Loan();
        // 1. Identificadores de referencias externas
        loan.setClientId(client.getIdCustomer());
        loan.setToolId(tool.getIdTool());
        loan.setClientRut(client.getRut());
        loan.setToolName(tool.getName());
        loan.setToolCategory(tool.getCategory());
        loan.setToolLoanFee(tool.getLoanFee());
        loan.setDeliveryDate(deliveryDate);
        loan.setReturnDate(expectedReturnDate);
        // 5. Estados iniciales
        loan.setLoanStatus(true);  // Préstamo activo
        loan.setPenalty(false);    // Inicia sin multa
        loan.setPenaltyTotal(0);   // Total de multa inicial
        loan.setPenaltyForDelay(0);

        DtoLoan kloan = new DtoLoan();
        kloan.setClientId(client.getIdCustomer());
        kloan.setToolId(tool.getIdTool());
        kloan.setClientRut(client.getRut());
        kloan.setToolName(tool.getName());
        kloan.setToolCategory(tool.getCategory());
        kloan.setToolLoanFee(tool.getLoanFee());

        kloan.setDeliveryDate(deliveryDate);
        kloan.setReturnDate(expectedReturnDate);
        kloan.setLoanStatus(true);  // Préstamo activo
        kloan.setPenalty(false);    // Inicia sin multa
        kloan.setPenaltyTotal(0);   // Total de multa inicial
        kloan.setPenaltyForDelay(0);
        kloan.setEmail(email);

        tool.setEmail(email);
        loanRepo.save(loan);
        externalService.updateToolStatus(tool);
        externalService.notifyKardexLoan(kloan);
        return loan;
    }

    // ... (rest of methods)

    public List<LoanResponseDto> findAll() {
        LocalDate today = LocalDate.now();
        List<Loan> allLoans = loanRepo.findAll();
        List<Loan> toSave = new ArrayList<>();

        for (Loan loan : allLoans) {
            if (Boolean.TRUE.equals(loan.getLoanStatus())) {
                long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), today);
                long daysloanfee = ChronoUnit.DAYS.between(loan.getDeliveryDate(), loan.getReturnDate());
                int daysloanfeeInt = (int) daysloanfee;
                boolean hasPenalty = daysLate > 0;

                int penaltyVal = loan.getPenaltyForDelay() != null ? loan.getPenaltyForDelay() : 0;
                int feeVal = loan.getToolLoanFee() != null ? loan.getToolLoanFee() : 0;
                
                int totalPenalty = hasPenalty ? (int) (daysLate * penaltyVal) : 0;

                int newPriceToPay = (feeVal * daysloanfeeInt) + totalPenalty;
                
                loan.setPriceToPay(newPriceToPay);
                if (!java.util.Objects.equals(loan.getPenalty(), hasPenalty) || !java.util.Objects.equals(loan.getPenaltyTotal(), totalPenalty)) {
                    loan.setPenalty(hasPenalty);
                    loan.setPenaltyTotal(totalPenalty);
                    externalService.updateClientStatus(loan.getClientId());
                    toSave.add(loan);
                }
            }
        }

        if (!toSave.isEmpty()) {
            loanRepo.saveAll(toSave);
        }
        
        return loanRepo.findAllWithClientAndToolIds();
    }


    @Transactional
    public Loan returnLoan(Long loanId, LocalDate actualReturnDate, ReturnLoanDto dto) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        // Obtener datos de la herramienta desde el microservicio de Inventario
        ToolDto tool = Optional.ofNullable(externalService.getToolById(loan.getToolId()))
                .orElseThrow(() -> new IllegalStateException("Tool info not found"));

        loan.setLoanStatus(false);

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);
        if (daysLate > 0) {
            int multa = (int) (daysLate * tool.getPenaltyForDelay());
            loan.setPenalty(true);
            loan.setPenaltyTotal(multa);
        } else {
            loan.setPenalty(false);
            loan.setPenaltyTotal(0);
        }

        // Liberar herramienta en Inventory MS
        tool.setStatus(true);
        tool.setEmail(dto.getEmail());
        externalService.updateToolStatus(tool);

        loan.setPriceToPay(loan.getPriceToPay());

        loanRepo.save(loan);

        // Habilitar cliente en Client MS
        externalService.updateClientStatus(loan.getClientId());
        externalService.notifyKardexReturnLoan(dto);

        return loan;
    }

    @Transactional
    public Loan returnLoanDamageTool(Long loanId, LocalDate actualReturnDate, ReturnLoanDto dto) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        ToolDto tool = Optional.ofNullable(externalService.getToolById(loan.getToolId()))
                .orElseThrow(() -> new IllegalStateException("Tool info not found"));

        loan.setLoanStatus(false);

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);

        int delayPenalty = (daysLate > 0) ? (int) (daysLate * tool.getPenaltyForDelay()) : 0;
        int totalPenalty = delayPenalty + tool.getDamageValue();

        loan.setPenalty(Boolean.TRUE);
        loan.setPenaltyTotal(totalPenalty);

        tool.setUnderRepair(Boolean.TRUE);
        tool.setEmail(dto.getEmail());
        // Actualizar herramienta: status=true, underRepair=true en Inventory MS
        externalService.updateToolDamageStatus(tool);

        int currentPrice = loan.getPriceToPay() != null ? loan.getPriceToPay() : 0;
        loan.setPriceToPay(currentPrice + tool.getDamageValue());

        externalService.updateClientStatus(loan.getClientId());
        externalService.notifyKardexReturnLoanDamage(dto);

        return loanRepo.save(loan);
    }

    @Transactional
    public Loan returnLoanDeleteTool(Long loanId, LocalDate actualReturnDate, ReturnLoanDto dto) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        ToolDto tool = Optional.ofNullable(externalService.getToolById(loan.getToolId()))
                .orElseThrow(() -> new IllegalStateException("Tool info not found"));

        loan.setLoanStatus(false);

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);

        int delayPenalty = (daysLate > 0) ? (int) (daysLate * tool.getPenaltyForDelay()) : 0;
        int totalPenalty = delayPenalty + tool.getReplacementValue();

        loan.setPenalty(Boolean.TRUE);
        loan.setPenaltyTotal(totalPenalty);

        tool.setDeleteStatus(Boolean.TRUE);
        tool.setEmail(dto.getEmail());

        // Actualizar herramienta: status=false, underRepair=false, deleteStatus=false
        externalService.returnLoanDeleteTool(tool);

        int currentPrice = loan.getPriceToPay() != null ? loan.getPriceToPay() : 0;
        loan.setPriceToPay(currentPrice + tool.getReplacementValue());

        externalService.updateClientStatus(loan.getClientId());
        externalService.notifyKardexReturnLoanDelete(dto);

        return loanRepo.save(loan);
    }

    public List<Long> findClientDelayed(){
        return loanRepo.findClientIdsWithDelayedLoans();
    }

    public List<Loan> findActiveAndOnTimeLoans() {
        return loanRepo.findActiveAndOnTimeLoans();
    }

    public List<Loan> findActiveAndDelayedLoans() {
        return loanRepo.findActiveAndDelayedLoans();
    }

    public List<ToolRankingDto> findMostLoanedToolsWithDetails() {
        return loanRepo.findMostLoanedToolsWithDetails();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepo.findById(id);
    }


    public void checkAndSetPenalties() {
        // 1. Obtener solo los préstamos activos
        List<Loan> activeLoans = loanRepo.findByLoanStatusTrue();
        LocalDate today = LocalDate.now();

        for (Loan loan : activeLoans) {
            // 2. Verificar si la fecha de hoy es posterior a la fecha de retorno
            if (today.isAfter(loan.getReturnDate())) {

                // 3. Marcar como penalizado el loan y el cliente
                loan.setPenalty(true);
                if(externalService.getClientByRut(loan.getClientRut()).getStatus()==Boolean.TRUE ){
                    externalService.updateClientStatus(loan.getClientId());
                }
                // 4. Calcular días de atraso
                long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), today);

                // 5. Calcular monto total de la multa (Días * Valor diario de multa)
                if (loan.getPenaltyForDelay() != null) {
                    int totalPenalty = (int) (daysLate * loan.getPenaltyForDelay());
                    loan.setPenaltyTotal(totalPenalty);
                }

                // 6. Guardar los cambios en el préstamo actual
                loanRepo.save(loan);
            } else {
                // Opcional: Asegurar que si no está atrasado, el penalty sea false
                loan.setPenalty(false);
                loan.setPenaltyTotal(0);
                loanRepo.save(loan);
            }
        }
    }

}
