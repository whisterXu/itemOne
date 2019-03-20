package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Address;
import com.pinyougou.pojo.Order;
import com.pinyougou.pojo.PayLog;
import com.pinyougou.service.AddressService;
import com.pinyougou.service.OrderService;
import com.pinyougou.service.WeiXinPayService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public boolean save(@RequestBody Order order, HttpServletRequest request) {
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
    public Map<String, String> genPayCode(HttpServletRequest request) {
        String username = request.getRemoteUser();
        //据用户id查询支付日志 ,返回支付日志对象
        PayLog payLog = orderService.findPayLogFromRedis(username);
        //获得交易订单号和总金额
        String outTradeNo = payLog.getOutTradeNo();Long totalFee = payLog.getTotalFee();
        //返回map集合封装参数
        return weiXinPayService.genPayCode(outTradeNo, String.valueOf(totalFee));
    }

    /**
     * 查询订单支付状态
     * @param outTradeNo  订单号码
     * @return  返回map集合封装参数
     */
    @GetMapping("/queryPayStatus")
    public Map<String,String> queryPayStatus(String outTradeNo){
        //定义map集合封装返回参数
        Map<String,String> data = new HashMap<>(16);

        //调用服务层方法查询订单状态
        Map<String, String> resultMap = weiXinPayService.queryPayStatus(outTradeNo);
        //定义变量
        String statusS = "SUCCESS" ,statusF = "FAIL",mapKey = "tradeState";
        //判断返回结果
        if (resultMap != null && resultMap.size()>0) {
            data.put("totalFee",resultMap.get("totalFee"));
            if (statusS.equals(resultMap.get(mapKey))){
                data.put("status","1");
            }
            if (statusF.equals(resultMap.get(mapKey))){
                data.put("status","3");
            }
        }
        //返回结果
        return data;
    }
}
