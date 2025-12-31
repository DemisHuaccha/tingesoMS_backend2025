package com.tingeso.tingesoMS_loan.Services.Providers;

import com.tingeso.tingesoMS_loan.Dtos.ClientDto;
import com.tingeso.tingesoMS_loan.Dtos.ToolDto;
import com.tingeso.tingesoMS_loan.Dtos.ToolStatusDto;
import com.tingeso.tingesoMS_loan.Dtos.ToolFeeDto;
import com.tingeso.tingesoMS_loan.Dtos.CardexDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalServiceProvider {

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

    public void updateToolStatus(ToolStatusDto statusDto) {
        try {
            restTemplate.put("http://tingesoMS_inventory/api/tool/updateStatus", statusDto);
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
}
