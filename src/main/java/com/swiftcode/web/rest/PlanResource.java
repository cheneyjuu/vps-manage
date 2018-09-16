package com.swiftcode.web.rest;

import com.google.common.collect.Maps;
import com.swiftcode.domain.Plan;
import com.swiftcode.domain.User;
import com.swiftcode.security.SecurityUtils;
import com.swiftcode.service.PlanService;
import com.swiftcode.service.PurchasePlanService;
import com.swiftcode.service.ServerService;
import com.swiftcode.service.UserService;
import com.swiftcode.web.rest.vm.PlanVM;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 */
@RestController
@RequestMapping(value = "/api")
public class PlanResource {
    private final PlanService planService;
    private final PurchasePlanService purchasePlanService;
    private final UserService userService;

    @Autowired
    public PlanResource(PlanService planService, PurchasePlanService purchasePlanService,
                        UserService userService) {
        this.planService = planService;
        this.purchasePlanService = purchasePlanService;
        this.userService = userService;
    }

    /**
     * 套餐列表.
     *
     * @return 套餐列表
     */
    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> planList() {
        List<Plan> plans = planService.findAllPlan();
        return ResponseEntity.ok(plans);
    }

    /**
     * 添加新套餐.
     *
     * @param vm 参数
     * @return 已添加的套餐
     */
    @SneakyThrows
    @PostMapping("/plans")
    public ResponseEntity<?> addPlan(@RequestBody PlanVM vm) {
        PlanVM aVM = planService.addPlan(vm);
        return ResponseEntity.created(new URI("")).body(aVM);
    }

    /**
     * 用户购买套餐.
     *
     * @param id 套餐id
     * @return 收款码
     */
    @GetMapping("/plans/{id}/buy")
    public ResponseEntity<?> userBuyPlan(@PathVariable(value = "id") Long id) {
        Plan plan = planService.findById(id).orElseThrow(() -> new IllegalArgumentException("find plan error"));
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new IllegalArgumentException("get current user login error"));
        User user = userService.getUserWithAuthoritiesByLogin(currentUserLogin).orElseThrow(() -> new IllegalArgumentException("get current user error"));
        String codePath = purchasePlanService.buy(user, plan);
        Map<String, String> map = Maps.newHashMap();
        map.put("errCode", "0");
        map.put("data", codePath);
        return ResponseEntity.ok(map);
    }
}
