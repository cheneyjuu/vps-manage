package com.swiftcode.service;

import com.google.common.collect.Lists;
import com.swiftcode.VpsManageApp;
import com.swiftcode.domain.Order;
import com.swiftcode.domain.Plan;
import com.swiftcode.domain.User;
import com.swiftcode.repository.OrderRepository;
import com.swiftcode.repository.PlanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VpsManageApp.class)
@Transactional
public class UserBuyPlanTest {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchasePlanService purchasePlanService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        Plan plan10 = new Plan("体验套餐", 10L, "100G", "100M", 2, 180);
        Plan plan25 = new Plan("视频套餐", 25L, "300G", "300M", 2, 180);
        List<Plan> planList = Lists.newArrayList(plan10, plan25);
        planRepository.saveAll(planList);
    }

    @Test
    public void testUserBuyPlan() {
        int orderSize = orderRepository.findAll().size();
        Plan plan = planRepository.getOne(1L);
        User user = userService.getUserWithAuthoritiesByLogin("admin").orElseThrow(IllegalArgumentException::new);
        String codePicture = purchasePlanService.buy(user, plan);
        assertThat(codePicture).contains(String.valueOf(plan.getPrice()));

        Plan plan2 = planRepository.getOne(2L);
        String codePicture2 = purchasePlanService.buy(user, plan2);
        assertThat(codePicture2).contains(String.valueOf(plan2.getPrice()));

        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(orderSize + 2);
        assertThat(orders.get(0).getLogin()).isEqualTo(user.getLogin());
        assertThat(orders.get(0).getPlanInfo().getPrice()).isEqualTo(plan.getPrice());
    }
}
