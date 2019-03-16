package com.pinyougou.portal.controller;

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
 * @time: 0:56 星期日
 * @vision: 1.0
 * --------------------------------
 */
@RestController
public class LoginController {

    @GetMapping("/user/showName")
    public Map<String,String> showName(HttpServletRequest request){
        try{
            //获取远程登录用户名
            String username = request.getRemoteUser();
            //创建map集合封装登录用户名
            Map<String,String> map = new HashMap<>(16);
            map.put("loginName", username);
            return map;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
