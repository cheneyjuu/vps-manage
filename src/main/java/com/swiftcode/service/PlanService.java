package com.swiftcode.service;

import com.swiftcode.domain.Plan;
import com.swiftcode.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author chen
 */
@Service
public class PlanService {
    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Optional<Plan> findById(Long id) {
        return planRepository.findById(id);
    }
}
