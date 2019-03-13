package com.pinyougou.sms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务控制器
 * @author whister
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Reference(timeout = 10000)
    private SmsService smsService;

    /**
     *
     * @param phone 手机号码
     * @param signName 签名
     * @param templateCode  短信模板
     * @param templateParam 模板参数
     * @return  返回true或false
     */
    @PostMapping("/sendSms")
    public Map<String ,Object> sendSms(String phone,String signName,
                                       String templateCode,String templateParam){
        try{
            //调用服务层发送短信方法，传入参数
            boolean success = smsService.sendSms(phone, signName, templateCode, templateParam);
            //创建集合对象
            HashMap<String, Object> map = new HashMap<>(16);
            //把结果添加到集合
            map.put("success", success);
            return map;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
