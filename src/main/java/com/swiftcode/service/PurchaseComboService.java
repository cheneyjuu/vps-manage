package com.swiftcode.service;

import com.swiftcode.domain.Combo;
import com.swiftcode.domain.User;
import org.springframework.stereotype.Service;

/**
 * 用户购买套餐
 * @author chen
 */
@Service
public class PurchaseComboService {

    public String buy(User user, Combo combo) {
        return "/path/of/ReceiptCode";
    }
}
