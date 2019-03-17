package com.pinyougou.search.controller;

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
 * @time: 14:49 星期日
 * @vision: 1.0
 * --------------------------------
 */
@RestController
public class LoginController {

    /**
     *   获取用户登录用户名
     * @param request 请求对象
     * @return  返回map封装用户名
     */
    @GetMapping("/user/showName")
    public Map<String,String> showName(HttpServletRequest request){
        try {
            String username = request.getRemoteUser();
            Map<String,String> map = new HashMap<>(16);
            map.put("loginName",username);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
