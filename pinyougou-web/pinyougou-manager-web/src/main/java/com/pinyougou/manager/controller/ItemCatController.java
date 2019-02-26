package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 分类服务控制器
 *
 * @author whister
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    /**
     * 带父级ID 分页查询
     *
     * @param parentId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/findByPage")
    public PageResult findByPage(Long parentId, Integer page, Integer rows) {
        try {
            PageResult pageResult = itemCatService.findByPage(parentId, page, rows);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加分类方法
     * @param itemCat
     * @return
     */
    @PostMapping("/save")
    public boolean save(@RequestBody ItemCat itemCat){
        try {
            itemCatService.save(itemCat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改分类的方法
     * @param itemCat
     * @return
     */
    @PostMapping("/update")
    public boolean update(@RequestBody ItemCat itemCat){
        try {
            itemCatService.update(itemCat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            itemCatService.delete(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
