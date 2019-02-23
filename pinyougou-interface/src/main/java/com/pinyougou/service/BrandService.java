package com.pinyougou.service;

import com.pinyougou.pojo.Brand;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 服务接口
 * @author whister
 */
public interface BrandService {

    /**
     * 查询全部brand方法
     * @param brand
     * @param currentPage
     * @param rows
     * @return List<Object>
     */
    PageResult findByPage(Brand brand,Integer currentPage,Integer rows);

    /**
     * 保存方法
     * @param  brand
     */
    void save(Brand brand);
    /**
     * 更新方法
     * @param  brand
     */
    void update(Brand brand);
    /**
     * 批量删除方法
     * @param  ids
     */
    void deleteAll(Long[] ids);

    /**
     * 查询品牌
     * @return
     */
    List<Map<String ,Object>> findBrandList();
}
