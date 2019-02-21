package com.pinyougou.service;

import com.pinyougou.pojo.Specification;
import pinyougou.conmmon.pojo.PageResult;

import java.io.Serializable;

/**
 * 规格服务接口
 * @author whister
 *
 */
public interface SpecificationService {

    /**
     * 带条件分页查询
     * @param specification
     * @param currentPage
     * @param rows
     * @return PageResult
     */
    PageResult findByPage(Specification specification,Integer currentPage,Integer rows);

    /**
     * 保存的方法
     * @param specification
     */
    void save(Specification specification);

    /**
     * 删除规格
     * @param ids
     */
    void deleteAll(Serializable[] ids);
}