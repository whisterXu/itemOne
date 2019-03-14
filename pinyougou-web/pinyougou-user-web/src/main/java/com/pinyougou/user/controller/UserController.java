package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.User;
import com.pinyougou.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户控制器
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/13
 * @time: 11:58 星期三
 * @vision: 1.0
 * --------------------------------
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(timeout = 10000)
    private UserService userService;

    /**
     * 保存用户注册信息
     *
     * @param user  用户对象封装参数
     * @return 返回true 或 false
     */
    @PostMapping("/save")
    public boolean save(@RequestBody User user,String smsCode) {
        try {
            //调用验证短信验证码的方法
            boolean result = userService.checkCode(user.getPhone(), smsCode);

            if (result) {
                //调用服务层save方法保存用户注册信息
                userService.save(user);
                //返回结果
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/sendCode")
    public boolean sendCode(String phone){
        try{
        if (StringUtils.isNoneBlank(phone)){
            //发送验证码
            userService.sendCode(phone);
            return true;
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
