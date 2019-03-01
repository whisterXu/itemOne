package com.pinyougou.service;

import com.pinyougou.pojo.Goods;

public interface GoodsService {

    /**
     * 添加商品基本信息
     * @param goods
     */
    void save(Goods goods);
}
