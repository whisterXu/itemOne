package com.pinyougou.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: LoginController
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/17
 * @time: 15:18 星期日
 * @vision: 1.0
 * --------------------------------
 */

/**
 *  用户登录控制器
 */
@RestController
public class LoginController {

    /**
     * 获取用户登录用户名
     * @param request
     * @return
     */
    @GetMapping("/user/showName")
    public Map<String,String> showName(HttpServletRequest request){
        try {
            String username = request.getRemoteUser();
            System.out.println("用户登录用户名" + username);
            Map<String,String> map = new HashMap<>();
            map.put("loginName", username);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
