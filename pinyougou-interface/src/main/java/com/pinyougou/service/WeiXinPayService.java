package com.pinyougou.service;

import java.util.Map;

/**
 * @description: WeiXinPayService
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/19
 * @time: 16:11 星期二
 * @vision: 1.0
 * --------------------------------
 */
public interface WeiXinPayService {
    /**
     * 生成二维码
     * @param outTradeNo 订单号
     * @param totalFee   总金额
     * @return  返回map集合封装参数
     */
    Map<String,String> genPayCode(String outTradeNo, String totalFee);

    /**
     *  查询支付状态
     * @param outTradeNo 订单号
     * @return  返回map集合封装参数
     */
    Map<String,String> queryPayStatus(String outTradeNo);
}
