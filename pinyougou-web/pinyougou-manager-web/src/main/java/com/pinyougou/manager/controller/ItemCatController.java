package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 分类服务控制器
 * @author whister
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    @GetMapping("/findByPage")
    public PageResult findByPage(Long parentId , Integer page, Integer rows){
        try {
            PageResult pageResult = itemCatService.findByPage(parentId, page, rows);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
