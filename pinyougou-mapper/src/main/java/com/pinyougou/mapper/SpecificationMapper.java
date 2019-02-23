package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

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
     * 批量删除(规格)
     * @param ids
     */
    void deleteAll(Long[] ids);

    /**
     * 查询全部规格
     * @return
     */
    @Select("select id,spec_name as text from tb_specification order by id asc ;")
    List<Map<String,Object>> findSpecificationList();
}
