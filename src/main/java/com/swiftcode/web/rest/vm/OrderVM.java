package com.swiftcode.web.rest.vm;

import com.swiftcode.domain.Order;
import com.swiftcode.domain.vo.PlanInfo;
import lombok.Data;

/**
 * @author chen
 */
@Data
public class OrderVM {
    private String login;
    private String planName;

    public static OrderVM entityToVM(Order order) {
        OrderVM vm = new OrderVM();
        PlanInfo planInfo = order.getPlanInfo();
        vm.setLogin(order.getLogin());
        vm.setPlanName(planInfo.getName());
        return vm;
    }
}
