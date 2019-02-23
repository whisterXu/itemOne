package com.pinyougou.mapper;

import com.pinyougou.pojo.TypeTemplate;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 模板管理数据访问层
 * @author whister
 */
public interface TypeTemplateMapper extends Mapper<TypeTemplate> {
    /**
     * 带条件分页查询
     * @param typeTemplate
     */
    List<TypeTemplate> findByCondition(TypeTemplate typeTemplate);


}
