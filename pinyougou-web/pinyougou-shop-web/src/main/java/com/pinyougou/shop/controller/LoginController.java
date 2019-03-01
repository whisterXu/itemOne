package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 显示登录用户名的控制器
 * @author whister
 */
@RestController
public class LoginController {

    @GetMapping("/showLoginName")
    public Map<String ,String> showLoginName(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String, String> map = new HashMap<>(16);
        map.put("loginName", username);
        return map;
    }
}
