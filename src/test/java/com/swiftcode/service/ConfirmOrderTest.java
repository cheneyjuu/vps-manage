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
public class ConfirmOrderTest {
    private static final String ADMIN_LOGIN = "admin";
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchasePlanService purchasePlanService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        Plan plan10 = new Plan("体验套餐", 10L, "100G", "100M", 2, 180);
        Plan plan25 = new Plan("视频套餐", 25L, "300G", "300M", 2, 180);
        List<Plan> planList = Lists.newArrayList(plan10, plan25);
        planRepository.saveAll(planList);

        Plan plan = planRepository.getOne(1L);
        User user = userService.getUserWithAuthoritiesByLogin(ADMIN_LOGIN).orElse(null);
        purchasePlanService.buy(user, plan);
    }

    @Test
    public void testAdminConfirmOrder() {
        Order order = orderRepository
            .findByUserInfoLogin(ADMIN_LOGIN)
            .orElseThrow(() -> new IllegalArgumentException("order not found"));
        assertThat(order.getStatus()).isEqualTo(0);

        orderService.confirmPayed(order);
        assertThat(order.getStatus()).isEqualTo(2);
    }
}
