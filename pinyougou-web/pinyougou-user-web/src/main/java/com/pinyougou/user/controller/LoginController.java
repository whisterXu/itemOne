package com.pinyougou.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: LoginController
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/15
 * @time: 0:32 星期五
 * @vision: 1.0
 * --------------------------------
 */
@RestController
public class LoginController {

    @GetMapping("/user/showName")
    public Map<String,String> showName(){
        try {
            //获取用户名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            //map集合封装
            HashMap<String, String> map = new HashMap<>(16);
            map.put("loginName",username);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
