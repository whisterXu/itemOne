package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Address;
import com.pinyougou.pojo.Order;
import com.pinyougou.service.AddressService;
import com.pinyougou.service.OrderService;
import com.pinyougou.service.WeiXinPayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pinyougou.conmmon.utils.IdWorker;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description: OrderController
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/17
 * @time: 17:22 星期日
 * @vision: 1.0
 * --------------------------------
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference(timeout = 10000)
    private AddressService addressService;

    @Reference(timeout = 10000)
    private WeiXinPayService weiXinPayService;

    @Reference(timeout = 10000)
    private OrderService orderService;

    /**
     * 查询用户地址
     *
     * @return 返回用户地址集合
     */
    @GetMapping("/findAddress")
    public List<Address> findAddress() {
        try {
            //调用用户地址服务方法查询全部用户地址
            return addressService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建订单,保存订单方法
     *
     * @param order order对象作为参数封装
     * @return 返回boolean类型
     */
    @PostMapping("/save")
    public boolean save(Order order, HttpServletRequest request) {
        try {
            //获取用户登录名
            String username = request.getRemoteUser();
            order.setUserId(username);
            //设置订单来源
            order.setSourceType("2");
            orderService.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成二维码
     * @return 返回map集合数据类型
     */
    @GetMapping("/genPayCode")
    public Map<String, Object> genPayCode() {
        IdWorker idWorker = new IdWorker();
        long orderId = idWorker.nextId();
        return weiXinPayService.genPayCode(orderId + "", "0.1");
    }
}
