package com.pinyougou.service;

import com.pinyougou.solr.SolrItem;

import java.util.List;
import java.util.Map;

/**
 * 搜索商品服务接口 (对索引库进行操作)
 * @author whister
 */
public interface ItemSearchService {

    /**
     * 商品查询的方法
     * @param searchParamMap
     * @return Map<String,Object>
     */
    Map<String,Object> search(Map<String,Object> searchParamMap);

    /**
     *  添加或修改商品索引库索引
     * @param solrItemList
     */
    void saveOrUpdate(List<SolrItem> solrItemList);


    /**
     * 删除sku商品索引库索引
     * @param ids
     */
    void deleteSolrDoc(Long[] ids);
}
