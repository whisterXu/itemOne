package com.pinyougou.mapper;

import com.pinyougou.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 *  数据访问层
 * @author whister
 */
public interface GoodsMapper extends Mapper<Goods> {
    /**
     *  带条件查询商品
     * @param goods
     * @return List<Goods>
     */
    List<Map> findByCondition(Goods goods);

    /**
     * 修改商品状态码
     * @param ids
     * @param auditStatus
     * @param columnName
     */
    void updateStatus(@Param("ids") Long[] ids , @Param("auditStatus") String auditStatus,@Param("columnName") String columnName);
}
