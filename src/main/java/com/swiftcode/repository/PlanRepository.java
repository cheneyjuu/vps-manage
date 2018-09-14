package com.swiftcode.repository;

import com.swiftcode.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chen
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
