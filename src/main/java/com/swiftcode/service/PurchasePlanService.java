package com.swiftcode.service;

import com.swiftcode.domain.Plan;
import com.swiftcode.domain.User;
import org.springframework.stereotype.Service;

/**
 * 用户购买套餐
 * @author chen
 */
@Service
public class PurchasePlanService {

    public String buy(User user, Plan plan) {
        return "/path/of/ReceiptCode";
    }
}
