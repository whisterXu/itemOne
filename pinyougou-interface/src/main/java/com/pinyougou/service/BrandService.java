package com.pinyougou.service;

import com.pinyougou.pojo.Brand;

import java.util.List;

/**
 * 服务接口
 */
public interface BrandService {

    /**
     * 查询全部brand方法
     * @return
     */
    List<Brand> findAll();
}
