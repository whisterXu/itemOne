package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.Cart;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.OrderItem;
import com.pinyougou.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: CartServiceImpl
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/16
 * @time: 9:26 星期六
 * @vision: 1.0
 * --------------------------------
 */
@Service(interfaceName = "com.pinyougou.service.CartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 添加SKU商品到购物车
     *
     * @param cartList  购物车(一个Cart对应一个商家)
     * @param itemId SKU商品id
     * @param num    购买数据
     * @return 修改后的购物车
     */
    @Override
    public List<Cart> addItemToCart(List<Cart> cartList, Long itemId, Integer num) {
        //根据itemId查询item商品Sku
        Item item = itemMapper.selectByPrimaryKey(itemId);
        //SKU商品是否存在
        if (item == null) {
            throw new RuntimeException("商品不存在!");
        }
        //商品状态
        String status = "1";
        //判断商品转态是否有效
        if (!status.equals(item.getStatus())){
            throw new RuntimeException("商品状态无效!");
        }

        //获取商家ID
        String sellerId = item.getSellerId();
        //根据商家ID搜索购物车列表中是否存在商家购物车
        Cart cart = searchCartBySellerId(cartList,sellerId);

        //判断商家购物车是否为空
        //为空,用户为购买过此商品
        if (cart == null){
            //创建一个新的购物车
            cart =  new Cart();
            //设置购物车的属性
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());

            //创建购物车订单明细
            OrderItem orderItem = createOrderItem(item,num);
           //创建订单明细集合
            List<OrderItem> orderItemList = new ArrayList<>();
            //订单明细添加到订单明细集合中
            orderItemList.add(orderItem);
            //将订单明细李彪设置到购物车属性中
            cart.setOrderItems(orderItemList);

            cartList.add(cart);
        }else {
            //不为空,则说明用户购买过商家的商品
            //根据SKU id搜索订单明细列表中是否已经存在相同商品
            OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItems(),itemId);

            //判断订单明细是否存在
            //为空,则说明该商品订单明细不存在,用户未购买过同一商品
            if (orderItem == null){
                //创建新的订单明细对象并设置属性
                orderItem = createOrderItem(item, num);
                //将订单明细添加到订单明细列表中
                cart.getOrderItems().add(orderItem);
            }else {
                //不为空,说明该商品订单明细已经存在,用户购买过同一商家的同一个商品

                //在原来的购物车订单明细中,同一商品,则数量相加
                orderItem.setNum(orderItem.getNum() + num);
                //小计=数量*商品价格
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));

                //判断购买数量是否等于0 ,如果等于0 ,删除商品订单明细
                if (orderItem.getNum() <= 0){
                    cart.getOrderItems().remove(orderItem);
                }
                //判断购物车中长度是否等于0 ,如果等于0 ,上车购物车
                if (cartList.size() == 0){
                    cartList.remove(cart);
                }
            }
        }
        return cartList;
    }


    /**
     * 根据SKU id搜索订单明细列表中是否已经存在相同商品
     * @param orderItems  商品订单明细
     * @param itemId   SKU商品
     * @return   返回  OrderItem 订单明细对象
     */
    private OrderItem searchOrderItemByItemId(List<OrderItem> orderItems, Long itemId) {
        //遍历orderItems列表
        for (OrderItem orderItem : orderItems) {
            //判断itemId是否相等,相等则为同一个SKU
            if (orderItem.getItemId().longValue() == itemId.longValue()) {
                return orderItem;
            }
        }
        return null;
    }

    /**
     * 为新购物车创建订单明细
     * @param item SKU商品
     * @param num  购买数量
     * @return 返回OrderItem 对象
     */
    private OrderItem createOrderItem(Item item, Integer num) {
        if (num < 0){
            throw new RuntimeException("非法数量");
        }
        //创建orderItem订单明细列表
        OrderItem orderItem = new OrderItem();
        //设置orderItem订单明细列表 属性
        orderItem.setItemId(item.getId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setNum(num);
        orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));
        orderItem.setPicPath(item.getImage());
        orderItem.setSellerId(item.getSeller());
        //返回orderItem对象
        return orderItem;
    }

    /**
     *  根据商家ID搜索购物车列表中是否存在商家购物车
     *
     * @param cartList 购物车列表
     * @param sellerId  商家id'
     * @return  返回购物车
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }
        return null;
    }


    /**
     * 从redis中获取购物车
     *
     * @param username 用户名
     * @return 返回购物车
     */
    @Override
    public List<Cart> findCartFromRedis(String username) {
        try {
            List<Cart> cartList = (List<Cart>) redisTemplate.boundValueOps("cart_" + username).get();
            if (cartList==null) {
                cartList  = new ArrayList<>();
            }
            return cartList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  将购物车添加到redis数据库
     * @param cartList  购物车列表
     * @param username  登录用户名
     * @return  返回一个购物车列表
     */
    @Override
    public void addCartToRedis(List<Cart> cartList, String username) {
        try {
            redisTemplate.boundValueOps("cart_" + username).set(cartList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并购物车  将将cookie中的购物车列表和redis中的的购物车列表合并
     * @param cookieCarts  cookie中的购物车列表
     * @param redisCarts  redis的购物车列表
     * @return  返回合并后的购物车
     */
    @Override
    public List<Cart> mergeCart(List<Cart> cookieCarts, List<Cart> redisCarts) {
        try{
            //遍历cookie中的购物车列表,获得购物车
            for (Cart cookieCart : cookieCarts) {
                //遍历cookie中商家的购物车,获得订单明细
                for (OrderItem orderItem : cookieCart.getOrderItems()) {
                    //循环获取cookie购物车列表信息,创建购物车和购物车订单明细 添加到redis购物车列表
                    //调用addItemToCart方法添加SKU商品到购物车,将购物车循环添加到redis数据库
                    redisCarts = addItemToCart(redisCarts, orderItem.getItemId(), orderItem.getNum());
                }
            }
            //返回合并后的购物车
            return redisCarts;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
