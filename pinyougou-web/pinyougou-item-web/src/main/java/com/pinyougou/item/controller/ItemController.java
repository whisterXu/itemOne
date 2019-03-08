package com.pinyougou.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 *  商品详情控制器
 * @author whister
 */
@Controller
public class ItemController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;

    @GetMapping("/{goodsId}")
    public String getGoods(@PathVariable("goodsId") long goodsId, Model model){
        Map<String, Object> data = goodsService.getGoodsByGoodsId(goodsId);
        model.addAllAttributes(data);
        return "item";
    }
}
