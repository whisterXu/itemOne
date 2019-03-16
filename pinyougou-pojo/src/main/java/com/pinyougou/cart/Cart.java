package com.pinyougou.cart;

import com.pinyougou.pojo.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @description: Cart
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/16
 * @time: 9:24 星期六
 * @vision: 1.0
 * --------------------------------
 */
public class Cart implements Serializable {
    /** 商家ID */
    private String sellerId;
    /** 商家名称 */
    private String sellerName;
    /** 购物车明细集合 */
    private List<OrderItem> orderItems;
    /** setter and getter method */
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "sellerId='" + sellerId + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
