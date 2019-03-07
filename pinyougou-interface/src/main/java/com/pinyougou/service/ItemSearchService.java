package com.pinyougou.service;

import java.util.Map;

/**
 * 搜索商品服务接口
 * @author whister
 */
public interface ItemSearchService {

    /**
     * 商品查询的方法
     * @param searchParamMap
     * @return Map<String,Object>
     */
    Map<String,Object> search(Map<String,Object> searchParamMap);
}
