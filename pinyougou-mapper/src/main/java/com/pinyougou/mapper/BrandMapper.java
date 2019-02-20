package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 数据访问层
 * @author whister
 */
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 批量删除方法.
     * @param ids
     */
    void deleteAll(Long[] ids);
    /**
     * 查询全部
     * @param brand
     * @return  List<Brand>
     */
    List<Brand> findByCondition(Brand brand);
}
