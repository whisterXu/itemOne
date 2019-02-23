package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import tk.mybatis.mapper.common.Mapper;

/**
 *  SpecificationOption 数据访问层
 * @author whister
 */
public interface SpecificationOptionMapper extends Mapper<SpecificationOption>  {

    /**
     * 添加规格选项
     * @param specification
     */
    void save(Specification specification);

    /**
     *  根据sqpecId外键删除规格选项数据
     * @param specId
     */
    void deleteAll(Long[] specId);

}
