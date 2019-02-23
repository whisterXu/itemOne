package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询所有的品牌
     * @return
     */
    @Select("select id, name as text from tb_brand order by id asc")
    List<Map<String,Object>> findAllByIdAndName();
}
