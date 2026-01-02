package com.tingeso.tingesoMS_inventory.Repository;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepositorie extends JpaRepository<Tool, Long> {
    java.util.List<Tool> findByStatusTrue();
    java.util.List<Tool> findByDeleteStatusFalse();
    
    @org.springframework.data.jpa.repository.Query("SELECT new com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto(t.category, t.name, COUNT(t)) FROM Tool t GROUP BY t.category, t.name")
    java.util.List<com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto> groupTools();
}
