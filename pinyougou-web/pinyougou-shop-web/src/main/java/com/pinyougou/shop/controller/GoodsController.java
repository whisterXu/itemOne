package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 商品控制器
 * @author whister
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;


    @GetMapping("/findByPage")
    public PageResult findByPage(Goods goods, Integer page,Integer rows){
        try{
//            获取页面登录用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//            判断是否为空
            if (goods != null && StringUtils.isNoneBlank(goods.getGoodsName())) {
//                转码
                goods.setGoodsName(new java.lang.String(goods.getGoodsName().getBytes("ISO8859-1"),"UTF-8"));
            }
//            设置goods对象中的sellerId
            goods.setSellerId(userName);
            return goodsService.findByPage(goods,page,rows);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 保存goods   SPU商品数据
     * @param goods
     * @return
     */
    @PostMapping("/save")
    public boolean save(@RequestBody  Goods goods){
        try {
//            获得用户登录的用户名
            java.lang.String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            将用户名设置到Goods对象中
            goods.setSellerId(username);
//            调用服务接口的方法
            goodsService.save(goods);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改上下架状态码 IsMarketable 实现上下架功能
     * @param ids
     * @param isMarketable
     * @return
     */
    @GetMapping("/updateIsMarketable")
    public boolean updateIsMarketable(Long[] ids ,String isMarketable){
        try{
            //调用goods服务层接口方法,实现了方法复用 (运营商管理后台和商家管理后台共同调用了同一个服务层方法)
            goodsService.updateStatus(ids,isMarketable,"is_marketable");
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     *  删除商品,修改商品状态
     * @param auditStatus
     * @return
     */
    @GetMapping("/delete")
    public boolean delete(Long[] ids ,String auditStatus){
        try {
//            调用服务接口的方法
            goodsService.updateStatus(ids,auditStatus,"is_delete");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
