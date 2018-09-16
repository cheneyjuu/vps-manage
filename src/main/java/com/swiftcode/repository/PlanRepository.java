package com.swiftcode.repository;

import com.swiftcode.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author chen
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {
    /**
     * find all available plans
     * @param isDisable disable flag, 0 is enable, 1 is disable
     * @return enable plans
     */
    List<Plan> findAllByIsDisable(Integer isDisable);
}
