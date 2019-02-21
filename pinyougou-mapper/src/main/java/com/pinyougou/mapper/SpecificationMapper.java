package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import tk.mybatis.mapper.common.Mapper;

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
}
