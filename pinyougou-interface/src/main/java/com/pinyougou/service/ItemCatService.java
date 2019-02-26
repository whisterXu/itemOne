package com.pinyougou.service;

import com.pinyougou.pojo.ItemCat;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 分类服务接口
 * @author whister
 */
public interface ItemCatService {
    /**
     * 分页查询
     * @param parentId
     * @param total
     * @param rows
     * @return PageResult
     */
    PageResult findByPage(Long parentId,Integer total,Integer rows);

    /**
     * 添加分类方法
     */
    void save(ItemCat itemCat);

    /**
     * 修改分类
     * @param itemCat
     */
    void update(ItemCat itemCat);

    /**
     * 批量删除分类
     * @param ids
     */
    void delete(Long[] ids);
}
