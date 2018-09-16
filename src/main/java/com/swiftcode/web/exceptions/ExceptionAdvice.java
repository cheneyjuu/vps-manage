package com.swiftcode.web.exceptions;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author chen
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({UserNotFountException.class, UserNotLoginException.class})
    @ResponseBody
    public Map<String, String> handleUserNotFountException(Exception e) {
        Map<String, String> map = Maps.newHashMap();
        map.put("errCode", "10001");
        map.put("errMsg", "查找用户信息出错");
        return map;
    }

    @ExceptionHandler({OrderNotFoundException.class})
    @ResponseBody
    public Map<String, String> handleOrderNotFountException(Exception e) {
        Map<String, String> map = Maps.newHashMap();
        map.put("errCode", "20001");
        map.put("errMsg", "订单信息不存在");
        return map;
    }
}
