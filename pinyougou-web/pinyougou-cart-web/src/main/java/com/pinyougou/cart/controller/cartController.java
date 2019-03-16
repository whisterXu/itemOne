package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.Cart;
import com.pinyougou.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pinyougou.conmmon.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description: cartController
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/15
 * @time: 22:10 星期五
 * @vision: 1.0
 * --------------------------------
 */
@RestController
@RequestMapping("/cart")
public class cartController {

    @Reference(timeout = 10000)
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     *  商品添加到购物车
     *
     * @param itemId sku商品ID
     * @param num  购买数量
     * @return  返回true或false
     */
    @GetMapping("/addCart")
    public boolean addCart(Long itemId, Integer num,HttpServletRequest request){

        try {
            //获取用户登录名
            String username = request.getRemoteUser();

            //查询cookie中是否存在购物车
            List<Cart> cartList = findCart();
            //将SKU添加到购物车
            cartList = cartService.addItemToCart(cartList,itemId,num);
            //把购物车保存到Cookie
            CookieUtils.setCookie(request,response, CookieUtils.CookieName.PINYOUGOU_CART,
                    JSON.toJSONString(cartList),3600*24,true);
            //返回购物车集合
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/findCart")
    public List<Cart> findCart(){
        List<Cart> carts = null;
        //获取用户登录名
        String username = request.getRemoteUser();
        //判断用户名是否存在
        //存在
        if (StringUtils.isNoneBlank(username)){
            //从redis数据库中读取数据
            carts = cartService.findCartFromRedis(username);
        }else {
            //从cookie中获取购物车.
            String cartStr = CookieUtils.getCookieValue(request, CookieUtils.CookieName.PINYOUGOU_CART, true);
            //如果cookie中购物车为空
            if (StringUtils.isBlank(cartStr)){
                cartStr = "[]";
            }
            //把字符串转成list集合
            carts = JSON.parseArray(cartStr, Cart.class);
        }
        return carts;
    }
}
