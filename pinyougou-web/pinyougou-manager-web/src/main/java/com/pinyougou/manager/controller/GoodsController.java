package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 分页查询商品
     * @param goods  商品对象封装参数
     * @param page  当前页
     * @param rows   页大小
     * @return       返回PageResult封装分页数据
     */
    @GetMapping("/findByPage")
    public PageResult findByPage(Goods goods, Integer page,Integer rows){
        try{

//            判断是否为空
            if (goods != null && StringUtils.isNoneBlank(goods.getGoodsName())) {
//            字符转码
                goods.setGoodsName(new String(goods.getGoodsName().getBytes("ISO8859-1"),"UTF-8"));
            }
//            设置goods对象中的sellerId
            goods.setAuditStatus("0");
            return goodsService.findByPage(goods,page,rows);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    /**
     *  修改商品状态   审核商品
     * @param auditStatus  审核商品状态
     * @return 返回boolean类型
     */
    @GetMapping("/updateStatus")
    public boolean updateStatus(Long[] ids ,String auditStatus){
        try {
//            调用服务接口的方法
            goodsService.updateStatus(ids,auditStatus,"audit_status");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *  修改商品状态   审核商品
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
