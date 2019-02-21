package com.pinyougou.service;

import com.pinyougou.pojo.Specification;
import pinyougou.conmmon.pojo.PageResult;

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
}
