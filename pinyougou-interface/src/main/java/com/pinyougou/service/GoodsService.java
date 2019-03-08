package com.pinyougou.service;

import com.pinyougou.pojo.Goods;
import pinyougou.conmmon.pojo.PageResult;

import java.util.Map;

/**
 * 商品服务接口
 * @author whister
 */
public interface GoodsService {

    /**
     *  带条件分页查询
     * @param goods
     * @param page
     * @param rows
     * @return  PageResult
     */
    PageResult findByPage(Goods goods, Integer page, Integer rows);

    /**
     * 添加商品基本信息
     * @param goods
     */
    void save(Goods goods);

    /**
     *  修改商品状态   审核商品
     * @param auditStatus
     * @param ids
     */
    void updateStatus(Long[] ids,String auditStatus,String columnName);

    /**
     *  根据goodId查询商品
     * @param goodsId
     * @return  Map<String,Object>
     */
    Map<String,Object> getGoodsByGoodsId(long goodsId);
}
