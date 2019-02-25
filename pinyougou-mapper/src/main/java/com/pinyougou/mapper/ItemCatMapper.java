package com.pinyougou.mapper;

import com.pinyougou.pojo.ItemCat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * 分类管理数据访问层
 * @author whister
 */
public interface ItemCatMapper extends Mapper<ItemCat> {

    /**
     * 根据parentId查询数据
     * @param parentId
     */
    @Select("select * from tb_item_cat where parent_id = #{parentId};")
    void findByParentId(@Param("parentId") Long parentId);
}
