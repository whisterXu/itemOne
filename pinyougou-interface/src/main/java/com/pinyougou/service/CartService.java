package com.pinyougou.service;

import com.pinyougou.cart.Cart;

import java.util.List;

/**
 * @description: CartService
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/16
 * @time: 9:26 星期六
 * @vision: 1.0
 * --------------------------------
 */
public interface CartService {
    /**
     * 添加SKU商品到购物车
     * @param cartList 购物车(一个Cart对应一个商家)
     * @param itemId SKU商品id
     * @param num 购买数据
     * @return 修改后的购物车
     */
    List<Cart> addItemToCart(List<Cart> cartList, Long itemId, Integer num);

    /**
     *  从redis中读取购物车
     * @param username 用户名
     * @return  返回购物车
     */
    List<Cart> findCartFromRedis(String username);
}
