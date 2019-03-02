package com.pinyougou.mapper;

import com.pinyougou.pojo.Goods;
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
}
