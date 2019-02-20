package com.pinyougou.service;

import com.pinyougou.pojo.Brand;

import java.util.List;

/**
 * 服务接口
 * @author whister
 */
public interface BrandService {

    /**
     * 查询全部brand方法
     * @return List<Object>
     */
    List<Brand> findAll();

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
}
