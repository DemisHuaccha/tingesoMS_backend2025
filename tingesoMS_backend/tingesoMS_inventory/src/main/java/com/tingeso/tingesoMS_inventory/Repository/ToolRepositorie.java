package com.tingeso.tingesoMS_inventory.Repository;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto;
import java.util.List;

@Repository
public interface ToolRepositorie extends JpaRepository<Tool, Long> {
    List<Tool> findByStatusTrue();
    List<Tool> findByDeleteStatusFalse();
    
    @Query("SELECT new com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto(t.category, t.name, COUNT(t)) FROM Tool t GROUP BY t.category, t.name")
    List<GroupToolsDto> groupTools();

    @Query("SELECT t FROM Tool t WHERE (:name IS NULL OR t.name = :name) AND (:category IS NULL OR t.category = :category) AND (:loanFee IS NULL OR t.loanFee = :loanFee)")
    List<Tool> findByNameCategoryAndLoanFee(@Param("name") String name, @Param("category") String category, @Param("loanFee") Integer loanFee);

    @Query("SELECT COUNT(t) FROM Tool t WHERE t.name = :name AND t.loanFee = :loanFee AND t.category = :category AND t.status = true AND t.underRepair = false AND t.deleteStatus = false")
    int countAvailableByNameAndCategory(@Param("name") String name, @Param("category") String category, @Param("loanFee") Integer loanFee);
}
