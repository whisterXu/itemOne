package com.pinyougou.service;

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
}
