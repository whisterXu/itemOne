package com.pinyougou.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.Cart;
import com.pinyougou.mapper.OrderItemMapper;
import com.pinyougou.mapper.OrderMapper;
import com.pinyougou.pojo.Order;
import com.pinyougou.pojo.OrderItem;
import com.pinyougou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.utils.IdWorker;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: OrderServiceImpl
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/19
 * @time: 10:44 星期二
 * @vision: 1.0
 * --------------------------------
 */
@Service(interfaceName = "com.pinyougou.service.OrderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     *  添加订单,创建订单
     *  @param order 订单对熊作为封装参数
     */
    @Override
    public void save(Order order) {
        //获取用户的购物车
        List<Cart> cartList = (List<Cart>) redisTemplate.boundValueOps("cart_"+order.getUserId()).get();
        if (cartList != null && cartList.size() > 0 ){
            for (Cart cart : cartList) {
                Order order1 = new Order();
                long orderId = idWorker.nextId();
                order1.setOrderId(orderId);
                order1.setPaymentType(order.getPaymentType());
                order1.setStatus("1");
                order1.setCreateTime(new Date());
                order1.setUpdateTime(new Date());
                order1.setUserId(order.getUserId());
                order1.setReceiverAreaName(order.getReceiverAreaName());
                order1.setReceiverMobile(order.getReceiverMobile());
                order1.setReceiver(order.getReceiver());
                order1.setSourceType("2");
                order1.setSellerId(cart.getSellerId());


                //定义订单总金额
                double totalMoney = 0;
                for (OrderItem orderItem : cart.getOrderItems()) {
                    //往订单表里面插入数据
                    long orderItemId = idWorker.nextId();
                    //设置订单明细ID
                    orderItem.setId(orderItemId);
                    //设置关联的订单id
                    orderItem.setOrderId(orderId);
                    //计算总金额
                    totalMoney +=orderItem.getTotalFee().doubleValue();
                    //###############往订单明细表中插入数据
                    orderItemMapper.insertSelective(orderItem);
                }
                //设置总金额
                order1.setPayment(new BigDecimal(totalMoney));
                //##########往订单表中插入数据
                orderMapper.insertSelective(order1);
            }
        }else {
            throw new RuntimeException("redis数据库中数据为空! ");
        }
        //删除用户redis中的购物车
        redisTemplate.delete("cart_" + order.getUserId());
    }

    /**
     * 修改方法
     *
     * @param order  订单对象封装参数
     */
    @Override
    public void update(Order order) {
    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {

    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {

    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Order findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Order> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param order
     * @param page
     * @param rows
     */
    @Override
    public List<Order> findByPage(Order order, int page, int rows) {
        return null;
    }

}
