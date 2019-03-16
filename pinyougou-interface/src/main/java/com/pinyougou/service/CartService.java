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

    /**
     * 将购物车添加到redis数据库
     * @param cartList  购物车列表
     * @param username  登录用户名
     * @return  放回一个购物车
     */
    void addCartToRedis(List<Cart> cartList, String username);

    /**
     *   合并购物车  将将cookie中的购物车列表和redis中的的购物车列表合并
     * @param cookieCarts  cookie中的购物车列表
     * @param carts  redis的购物车列表
     * @return  返回合并后的购物车
     */
    List<Cart> mergeCart(List<Cart> cookieCarts, List<Cart> carts);
}
