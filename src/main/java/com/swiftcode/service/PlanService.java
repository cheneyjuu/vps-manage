package com.swiftcode.service;

import com.swiftcode.domain.Plan;
import com.swiftcode.repository.PlanRepository;
import com.swiftcode.web.rest.vm.PlanVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Plan> findAllPlan() {
        return planRepository.findAllByIsDisable(0);
    }

    public PlanVM addPlan(PlanVM vm) {
        Plan aPlan = new Plan(vm.getName(), vm.getPrice(), vm.getTrafficLimit(), vm.getSpeedLimit(), vm.getIpLimit(), vm.getValidityPeriod());
        Plan plan = planRepository.save(aPlan);
        vm.setId(plan.getId());
        return vm;
    }
}
