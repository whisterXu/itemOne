package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.UserMapper;
import com.pinyougou.pojo.User;
import com.pinyougou.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.utils.HttpClientUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: UserServiceImpl
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/13
 * @time: 12:03 星期三
 * @vision: 1.0
 * --------------------------------
 */

@Service(interfaceName = "com.pinyougou.service.UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.signName}")
    private String signName;
    @Value("${sms.templateCode}")
    private String templateCode;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 添加方法
     *
     * @param user 用户对象封装参数
     */
    @Override
    public void save(User user) {
        try {
            //设置创建时间和更新时间
            user.setCreated(new Date());
            user.setUpdated(new Date());
            //给密码加密
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            //通用mapper方法保存用户信息
            userMapper.insertSelective(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送短信
     * @param phone 手机号码
     */
    @Override
    public boolean sendCode(String phone) {
        try {
            //生成随机数的验证码
            String code = UUID.randomUUID().toString().replaceAll("-", "")
                    .replaceAll("[a-zA-Z]", "").substring(0, 6);
            System.out.println("==============code:" + code);

            //创建HttpClientUtils工具类对象
            HttpClientUtils httpClientUtils = new HttpClientUtils(false);
            //封装请求参数
            Map<String,String> param = new HashMap<>(16);
            param.put("phone",phone);
            param.put("signName",signName);
            param.put("templateCode",templateCode);
            param.put("templateParam","{'number':" + code + "}");
            //发送请求
            String content = httpClientUtils.sendPost(smsUrl, param);
            Map map = JSON.parseObject(content, Map.class);
            boolean success = (boolean) map.get("success");
            System.out.println("发送短信状态success:" + success);
            //判断消息是否发送成功,发送成功就存入
            if (success){
                //存入redis数据库,key为手机号码,有效时间为90S
                redisTemplate.boundValueOps(phone).set(code,90,TimeUnit.SECONDS);
            }

            return success;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  验证手机号码是否相等
     * @param phone  手机号码
     * @param smsCode  验证码
     * @return
     */
    @Override
    public boolean checkCode(String phone, String smsCode) {
        try {
            //获取redis中的验证码通过手机号码
            String realCode = redisTemplate.boundValueOps(phone).get();
            System.out.println(smsCode + "==smsCode====================realCode:" + realCode);
            //判断验证码是否为空
            if (realCode != null && StringUtils.isNoneBlank(realCode)){
                System.out.println("进来对比用户输入的验证码是否正确!===========" + smsCode);
                //比较验证码是否相等
                if (realCode.equals(smsCode)){
                    //返回
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
