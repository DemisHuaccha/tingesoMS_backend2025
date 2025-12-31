package com.tingeso.tingesoMS_kardex.Service;

import com.tingeso.tingesoMS_kardex.Dtos.CardexDto;
import com.tingeso.tingesoMS_kardex.Entities.Cardex;
import com.tingeso.tingesoMS_kardex.Repository.CardexRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardexServiceImpl implements CardexService {

    @Autowired
    private CardexRepositorie cardexRepositorie;

    @Override
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

    @Override
    public List<CardexDto> findAllDto() {
        return cardexRepositorie.findAllCardex();
    }

    @Override
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

    @Override
    public List<CardexDto> findCardexTool(Long toolId) {
        return cardexRepositorie.findCardexByToolId(toolId);
    }
}
