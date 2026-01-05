package com.tingeso.tingesoMS_kardex.Service;

import com.tingeso.tingesoMS_kardex.Dtos.*;
import com.tingeso.tingesoMS_kardex.Entities.Cardex;
import com.tingeso.tingesoMS_kardex.Repository.CardexRepositorie;
import com.tingeso.tingesoMS_kardex.Services.Providers.ExternalServiceProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CardexServiceImpl {

    @Autowired
    private CardexRepositorie cardexRepositorie;


    @Autowired
    private ExternalServiceProvider externalService;

    /* 1. saveCardexLoan */
    @Transactional
    public void saveCardexLoan(DtoLoan loan) {
        Cardex cardex = new Cardex();
        Long toolId= loan.getIdTool();
        String email= loan.getEmail();

        DtoTool tool = Optional.ofNullable(externalService.getToolById(toolId))
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada"));

        DtoClient user = externalService.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
        cardex.setToolId(tool.getIdTool());
        cardex.setUserEmail(user.getEmail());
        cardex.setTypeMove("Create Loan");
        cardex.setMoveDate(LocalDate.now());
        cardex.setClientId(loan.getIdClient());
        cardex.setClientRut(loan.getClientRut());
        cardex.setLoanId(loan.getLoanId());
        cardex.setAmount(null);
        cardex.setDescription("User with email " + email + " register a loan where rut client is: " + loan.getClientRut());
        cardex.setQuantity(1);

        cardexRepositorie.save(cardex);
    }

    /* 2. saveCardexTool */
    @Transactional
    public void saveCardexTool(CreateToolDto toolT) {
        Cardex cardex = new Cardex();

        String email = toolT.getEmail();

        DtoClient user = externalService.getUserByEmail(toolT.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));


        // Corregido: No redeclarar 'tool', usamos el toolDtoIn pasado por parámetro o consultamos
        DtoTool toolInfo = Optional.ofNullable(externalService.getToolById(toolT.getIdTool()))
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada"));

        cardex.setUserEmail(user.getEmail());
        cardex.setToolId(toolInfo.getIdTool());
        cardex.setLoanId(null);
        cardex.setTypeMove("Create Tool");
        cardex.setAmount(null);
        cardex.setDescription("User with email " + email+ " register a tool with name: " + toolInfo.getName());
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(toolT.getQuantity());

        cardexRepositorie.save(cardex);
    }

    /* 3. saveCardexClient */
    @Transactional
    public void saveCardexClient( DtoClient client) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(client.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + client.getEmail()));

        cardex.setUserEmail(user.getEmail());
        cardex.setClientId(client.getIdCustomer());
        cardex.setClientRut(client.getRut());
        cardex.setLoanId(null);
        cardex.setToolId(null);
        cardex.setTypeMove("Create Client");
        cardex.setAmount(null);
        cardex.setDescription("User with email " + client.getEmail() + " register a client with rut: " + client.getRut());
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);

        cardexRepositorie.save(cardex);
    }

    /* 4. saveCardexUpdateTool */
    @Transactional
    public void saveCardexUpdateTool(CreateToolDto toolDto) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(toolDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + toolDto.getEmail()));

        cardex.setUserEmail(user.getEmail());
        cardex.setToolId(toolDto.getIdTool());
        cardex.setTypeMove("Update Tool");
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User with email " + toolDto.getEmail() + " update a tool with id: " + toolDto.getIdTool());

        cardexRepositorie.save(cardex);
    }

    /* 5. saveCardexUpdateStatusTool */
    @Transactional
    public void saveCardexUpdateStatusTool(ToolStatusDto toolDto) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(toolDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + toolDto.getEmail()));

        cardex.setUserEmail(user.getEmail());
        cardex.setToolId(toolDto.getIdTool());
        cardex.setTypeMove("Tool update status change");
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User with email " + toolDto.getEmail() + " change status of tool id: " + toolDto.getIdTool());

        cardexRepositorie.save(cardex);
    }

    /* 6. saveCardexRepairTool */
    @Transactional
    public void saveCardexRepairTool(ToolStatusDto toolDto) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(toolDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + toolDto.getEmail()));
        cardex.setUserEmail(user.getEmail());
        cardex.setToolId(toolDto.getIdTool());
        cardex.setTypeMove("Tool Repair status change to " + toolDto.getUnderRepair());
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User with email " + toolDto.getEmail() + " update repair status of tool id: " + toolDto.getIdTool());

        cardexRepositorie.save(cardex);
    }

    /* 7. saveCardexDeleteTool */
    @Transactional
    public void saveCardexDeleteTool(ToolStatusDto toolDto) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(toolDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + toolDto.getEmail()));
        cardex.setUserEmail(user.getEmail());
        cardex.setToolId(toolDto.getIdTool());
        cardex.setTypeMove("Tool Delete");
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User with email " + toolDto.getEmail() + " deleted tool id: " + toolDto.getIdTool());

        cardexRepositorie.save(cardex);
    }

    /* 8. saveCardexReturnLoan */
    @Transactional
    public void saveCardexReturnLoan(ReturnLoanDto loanDto) {
        Cardex cardex = new Cardex();

        DtoLoan loan = externalService.getLoanById(loanDto.getLoanId())
                .orElseThrow(() -> new IllegalArgumentException("Loan with id: " + loanDto.getLoanId() + " not found"));

        DtoClient user = externalService.getUserByEmail(loanDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + loanDto.getEmail()));

        cardex.setUserEmail(user.getEmail());
        cardex.setClientId(loan.getIdClient());
        cardex.setClientRut(loan.getClientRut());
        cardex.setLoanId(loan.getLoanId());
        cardex.setToolId(loan.getIdTool());
        cardex.setTypeMove("Loan Finished");
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User with email " + loanDto.getEmail() + " return loan id: " + loanDto.getLoanId());

        cardexRepositorie.save(cardex);
    }

    /* 9. saveCardexReturnLoanDamage */
    @Transactional
    public void saveCardexReturnLoanDamage(ReturnLoanDto loanDto) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(loanDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with email " + loanDto.getEmail() + " not found"));
        DtoLoan loan = externalService.getLoanById(loanDto.getLoanId())
                .orElseThrow(() -> new IllegalArgumentException("Loan with id: " + loanDto.getLoanId() + " not found"));

        cardex.setUserEmail(user.getEmail());
        cardex.setClientId(loan.getIdClient());
        cardex.setClientRut(loan.getClientRut());
        cardex.setLoanId(loan.getLoanId());
        cardex.setToolId(loan.getIdTool());
        cardex.setTypeMove("Loan Finished - Damaged");
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User " + loanDto.getEmail() + " return loan " + loanDto.getLoanId() + " with damage");

        cardexRepositorie.save(cardex);
    }

    /* 10. saveCardexReturnLoanDelete */
    @Transactional
    public void saveCardexReturnLoanDelete(ReturnLoanDto loanDto) {
        Cardex cardex = new Cardex();

        DtoClient user = externalService.getUserByEmail(loanDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with email " + loanDto.getEmail() + " not found"));

        DtoLoan loan = externalService.getLoanById(loanDto.getLoanId())
                .orElseThrow(() -> new IllegalArgumentException("Loan with id: " + loanDto.getLoanId() + " not found"));

        cardex.setUserEmail(user.getEmail());
        cardex.setClientId(loan.getIdClient());
        cardex.setClientRut(loan.getClientRut());
        cardex.setLoanId(loan.getLoanId());
        cardex.setToolId(loan.getIdTool());
        cardex.setTypeMove("Loan Finished - Replaced");
        cardex.setMoveDate(LocalDate.now());
        cardex.setQuantity(1);
        cardex.setDescription("User " + loanDto.getEmail() + " return loan " + loanDto.getLoanId() + " (Needs replacement)");

        cardexRepositorie.save(cardex);
    }


    @Transactional
    public void log(CardexDto logEntry) {
        Cardex cardex = new Cardex();
        cardex.setMoveDate(logEntry.getMoveDate() != null ? logEntry.getMoveDate() : LocalDate.now());
        cardex.setTypeMove(logEntry.getTypeMove());
        cardex.setDescription(logEntry.getDescription());
        cardex.setAmount(logEntry.getAmount());
        cardex.setQuantity(logEntry.getQuantity());
        cardex.setUserEmail(logEntry.getUserEmail());
        cardex.setToolId(logEntry.getToolId());
        cardex.setLoanId(logEntry.getLoanId());
        cardex.setClientRut(logEntry.getClientRut());
        
        cardexRepositorie.save(cardex);
    }

    public List<CardexDto> findAllDto() {
        return cardexRepositorie.findAllCardex();
    }

    public List<CardexDto> findForRangeDate(LocalDate start, LocalDate end, Long toolId) {
        if (toolId==null) {
            if (start == null && end == null) {
                return cardexRepositorie.findAllCardex();
            } else if (end == null) {
                return cardexRepositorie.findMovementsFromDate(start);
            } else if (start == null) {
                return cardexRepositorie.findMovementsUntilDate(end);
            }
            return cardexRepositorie.findCardexDateRange(start, end);
        }
        else{
            if (start == null && end == null) {
                return cardexRepositorie.findCardexByToolId(toolId);
            } else if (end == null) {
                return cardexRepositorie.findMovementsFromDateId(start, toolId);
            } else if (start == null) {
                return cardexRepositorie.findMovementsUntilDateId(end, toolId);
            }
            return cardexRepositorie.findCardexDateRangeId(start, end, toolId);
        }
    }

    public List<CardexDto> findCardexTool(Long toolId) {
        return cardexRepositorie.findCardexByToolId(toolId);
    }
}
