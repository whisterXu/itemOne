package com.pinyougou.cart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: LnginController
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/15
 * @time: 11:12 星期五
 * @vision: 1.0
 * --------------------------------
 */
@RestController
public class LnginController {

    @GetMapping("/user/showName")
    public Map<String,String> showName(HttpServletRequest request){
        //获取用户名
        String remoteUser = request.getRemoteUser();
        //map集合封装用户参数
        Map<String,String> map = new HashMap<>(16);
        map.put("loginName",remoteUser);
        //返回
        return map;
    }
}
