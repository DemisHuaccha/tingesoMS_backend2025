package com.tingeso.tingesoMS_inventory.Service;

import com.tingeso.tingesoMS_inventory.Dtos.*;
import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Repository.ToolRepositorie;
import com.tingeso.tingesoMS_inventory.Services.Providers.ExternalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ToolService {

    @Autowired
    private ToolRepositorie toolRepo;

    @Autowired
    private ExternalServiceProvider externalService;

    public Tool save(CreateToolDto dto) {

        Optional<DtoClient> resultado = externalService.getUserByEmail(dto.getEmail());

        if(!resultado.isPresent()){
            throw new IllegalArgumentException("User with email " + dto.getEmail() + " not found");
        }

        for(int i=1; i<dto.getQuantity();i++) {
            Tool tool = new Tool();
            tool.setStatus(Boolean.TRUE);
            tool.setDeleteStatus(Boolean.FALSE);
            tool.setUnderRepair(Boolean.FALSE);
            /*-------------------------------*/
            tool.setName(dto.getName());
            tool.setCategory(dto.getCategory());
            tool.setInitialCondition(dto.getInitialCondition());
            tool.setLoanFee(dto.getLoanFee());
            tool.setPenaltyForDelay(dto.getPenaltyForDelay());
            tool.setReplacementValue(dto.getReplacementValue());
            tool.setDescription(dto.getDescription());
            tool.setDamageValue(dto.getDamageValue());
            toolRepo.save(tool);
        }
        /*--------------------------------*/

        Tool lastTool = new Tool();
        lastTool.setStatus(Boolean.TRUE);
        lastTool.setDeleteStatus(Boolean.FALSE);
        lastTool.setUnderRepair(Boolean.FALSE);
        /*-------------------------------*/
        lastTool.setName(dto.getName());
        lastTool.setCategory(dto.getCategory());
        lastTool.setInitialCondition(dto.getInitialCondition());
        lastTool.setLoanFee(dto.getLoanFee());
        lastTool.setPenaltyForDelay(dto.getPenaltyForDelay());
        lastTool.setReplacementValue(dto.getReplacementValue());
        lastTool.setDescription(dto.getDescription());
        lastTool.setDamageValue(dto.getDamageValue());
        toolRepo.save(lastTool);
        dto.setIdTool(lastTool.getIdTool());
        externalService.notifyKardexTool(dto);
        return lastTool;
    }

    public Tool findById(Long id) {
        return toolRepo.findById(id).orElse(null);
    }
    
    public List<Tool> findAll() {
        return toolRepo.findAll();
    }
    
    public List<Tool> findAllAvalible() {
        return toolRepo.findByStatusTrue();
    }
    
    public List<Tool> findAllNotDelete() {
        return toolRepo.findByDeleteStatusFalse();
    }

    public Tool updateTool(CreateToolDto toolDto){
        Tool oldTool = toolRepo.findById(toolDto.getIdTool()).orElse(null);
        if(oldTool!=null){
            oldTool.setName(toolDto.getName());
            oldTool.setCategory(toolDto.getCategory());
            oldTool.setInitialCondition(toolDto.getInitialCondition());

            oldTool.setLoanFee(toolDto.getLoanFee());
            oldTool.setPenaltyForDelay(toolDto.getPenaltyForDelay());
            oldTool.setReplacementValue(toolDto.getReplacementValue());
            oldTool.setDamageValue(toolDto.getDamageValue());
            oldTool.setDescription(toolDto.getDescription());

            toolRepo.save(oldTool);
            externalService.notifyKardexUpdateTool(toolDto);
        }else {
            return null; 
        }
        return oldTool;
    }
    
    public void updateStatusTool(ToolStatusDto toolDto){
        Tool tool = toolRepo.findById(toolDto.getIdTool()).orElse(null);
        if(tool!=null){
             if(Boolean.TRUE.equals(toolDto.getStatus())){
                 tool.setStatus(Boolean.FALSE);
             }
             else {
                 tool.setStatus(Boolean.TRUE);
             }
        }
        externalService.notifyKardexUpdateStatusTool(toolDto);
        toolRepo.save(tool);
    }
    
    public void underRepairTool(CreateToolDto toolDto){
        Tool tool = toolRepo.findById(toolDto.getIdTool()).orElse(null);
        if(tool!=null){
            if(Boolean.FALSE.equals(toolDto.getUnderRepair())) { 
                 tool.setUnderRepair(Boolean.TRUE);
            }
            else {
                tool.setUnderRepair(Boolean.FALSE);
            }
        }
        externalService.notifyKardexRepairTool(toolDto);
        toolRepo.save(tool);
    }
    
    public void deleteTool(CreateToolDto toolDto){
        Tool tool = toolRepo.findById(toolDto.getIdTool()).orElse(null);
        if(tool!=null){
            if(Boolean.TRUE.equals(toolDto.getDeleteStatus())){
                tool.setDeleteStatus(Boolean.FALSE);
            }
            else {
                tool.setDeleteStatus(Boolean.TRUE);
            }
        }
        externalService.notifyKardexDeleteTool(toolDto);
        toolRepo.save(tool);
    }
    
    public List<Tool> filterTools(ToolRankingDto toolDto) {
        // Strict monolith filtering using local columns
        return toolRepo.findByNameCategoryAndLoanFee(toolDto.getNameTool(), toolDto.getCategoryTool(), toolDto.getFeeTool());
    }
    
    public List<GroupToolsDto> groupTools() {
        return toolRepo.groupTools();
    }
    
    public List<String> getConditions() {
        return Arrays.stream(InitialCondition.values())
                .map(Enum::name)
                .toList();
    }
    
    public int countAvailable(String name, String category, Integer loanFee) {
        return toolRepo.countAvailableByNameAndCategory(name, category, loanFee);
    }
}
