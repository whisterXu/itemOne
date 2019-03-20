package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.service.WeiXinPayService;
import org.springframework.beans.factory.annotation.Value;
import pinyougou.conmmon.utils.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付服务接口实现类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-15<p>
 */
@Service(interfaceName = "com.pinyougou.service.WeiXinPayService")
public class WeixinPayServiceImpl implements WeiXinPayService {

    /** 微信公众账号或开放平台APP的唯一标识 */
    @Value("${appid}")
    private String appid;
    /** 商户账号 */
    @Value("${partner}")
    private String partner;
    /** 商户密钥 */
    @Value("${partnerkey}")
    private String partnerkey;

    /** 统一下单接口请求URL */
    @Value("${unifiedorder}")
    private String unifiedorder;
    /** 查询订单接口请求URL */
    @Value("${orderquery}")
    private String orderquery;


    /**
     * 调用微信支付的系统的"统一下单接口"
     * 获取code_url生成支付二维码
     */
    @Override
    public Map<String,String> genPayCode(String outTradeNo, String totalFee){
        try{
            // 1. 定义Map集合封装请求参数
            Map<String, String> params = new HashMap<>();
            // 公众账号ID 	appid
            params.put("appid", appid);
            // 商户号	mch_id
            params.put("mch_id", partner);
            // 随机字符串	nonce_str
            params.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商品描述	body
            params.put("body", "品优购");
            // 商户订单号	out_trade_no
            params.put("out_trade_no", outTradeNo);
            // 标价金额	total_fee (单位：分)
            params.put("total_fee", totalFee);
            // 终端IP	spbill_create_ip
            params.put("spbill_create_ip", "127.0.0.1");
            // 通知地址	notify_url
            params.put("notify_url", "http://www.pinyougou.com");
            // 交易类型	trade_type NATIVE -Native支付
            params.put("trade_type", "NATIVE");

            // 把Map集合转化成xml请求参数 加签名sign参数
            String xmlParam = WXPayUtil.generateSignedXml(params, partnerkey);
            System.out.println("请求参数: " + xmlParam);


            // 2. 调用统一下单接口,得到响应数据(https请求)
            HttpClientUtils httpClientUtils = new HttpClientUtils(true);
            String xmlResData = httpClientUtils.sendPost(unifiedorder, xmlParam);
            System.out.println("==========响应数据：" + xmlResData);

            // 3. 获取响应数据，返回数据
            // 3.1 把xml格式转化成Map集合
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResData);


            // {outTradeNo : "", money :100, codeUrl : ''}
            Map<String,String> data = new HashMap<>(16);
            // 二维码链接	code_url
            data.put("codeUrl", map.get("code_url"));
            data.put("outTradeNo", outTradeNo);
            data.put("totalFee", totalFee);
            return data;

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 查询订单支付状态
     * @param outTradeNo 订单号
     * @return 返回map集合封装参数
     */
    @Override
    public Map<String, String> queryPayStatus(String outTradeNo) {
        Map<String,String> param = new HashMap<>();
        // 1. 定义Map集合封装请求参数
        Map<String, String> params = new HashMap<>();

        // 公众账号ID 	appid
        params.put("appid", appid);
        // 商户号	mch_id
        params.put("mch_id", partner);
        // 随机字符串	nonce_str
        params.put("nonce_str", WXPayUtil.generateNonceStr());
        // 商户订单号	out_trade_no
        params.put("out_trade_no", outTradeNo);

        try {
            // 把Map集合转化成xml请求参数 加签名sign参数
            String xmlParam = WXPayUtil.generateSignedXml(params, partnerkey);
            System.out.println("请求参数: " + xmlParam);
            //使用httpClientUtils工具类发送请求
            HttpClientUtils httpClientUtils = new HttpClientUtils(true);
            //返回结果
            String resultXml = httpClientUtils.sendPost(orderquery, xmlParam);
            System.out.println("返回结果 : resultXml ==" + resultXml);
            //将xml格式转成map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);


            //定义map集合封装请求参数
            Map<String,String> data = new HashMap<>(16);
            // 二维码链接	code_url
            data.put("tradeState", resultMap.get("trade_state"));
            data.put("totalFee", resultMap.get("total_fee"));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
