package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * specification 数据访问层
 * @author  whister.xu
 */
public interface SpecificationMapper extends Mapper<Specification> {

    /**
     * 带条件查询Specification
     * @param specification
     * @return List<Specification>
     */
    List<Specification> findByCondition(Specification specification);

    /**
     * 添加规格选项
     * @param specification
     */
    void save(Specification specification);

    /**
     * 批量删除(规格)
     * @param ids
     */
    void deleteAll(Serializable[] ids);
}