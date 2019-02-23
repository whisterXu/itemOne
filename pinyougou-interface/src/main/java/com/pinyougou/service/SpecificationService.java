package com.pinyougou.service;

import com.pinyougou.pojo.Specification;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;
import java.util.Map;

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
     * 修改的方法
     * @param specification
     */
    void update(Specification specification);

    /**
     * 删除规格
     * @param ids
     */
    void deleteAll(Long[] ids);

    /**
     * 查询全部规格 返回给模板管理的增加选项
     * 格式:{data: [{id: 1, text: '联想'}, {id: 2, text: '华为'}, {id: 3, text: '小米'}]};
     * @return List<Map<String,Object>>
     */

    List<Map<String,Object>> findSpecificationList();
}
