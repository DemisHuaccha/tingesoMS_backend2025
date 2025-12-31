package com.tingeso.tingesoMS_inventory.Repository;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepositorie extends JpaRepository<Tool, Long> {
}
