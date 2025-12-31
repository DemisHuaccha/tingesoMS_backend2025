package com.tingeso.tingesoMS_fee.Repository;

import com.tingeso.tingesoMS_fee.Entities.ToolFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolFeeRepository extends JpaRepository<ToolFee, Long> {
}
