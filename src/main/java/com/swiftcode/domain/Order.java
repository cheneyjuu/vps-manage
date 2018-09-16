package com.swiftcode.domain;

import com.swiftcode.domain.base.AbstractEntity;
import com.swiftcode.domain.vo.PlanInfo;
import com.swiftcode.domain.vo.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author chen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sc_order")
public class Order extends AbstractEntity {
    private static final Integer UN_PAY = 0;
    private static final Integer TO_BE_CONFIRMED = 1;
    private static final Integer PAYED = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private PlanInfo planInfo;
    private Integer status = UN_PAY;

    public void createOrder(User user, Plan plan) {
        this.planInfo = new PlanInfo(plan.getName(), plan.getPrice(), plan.getTrafficLimit(),
            plan.getSpeedLimit(), plan.getIpLimit(), plan.getValidityPeriod());
        this.login = user.getLogin();
    }

    public void toPayed() {
        this.status = PAYED;
    }
}
