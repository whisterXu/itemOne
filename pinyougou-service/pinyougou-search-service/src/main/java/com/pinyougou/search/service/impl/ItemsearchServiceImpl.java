package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  商品搜搜的服务提供者
 *  @author whister
 */
@Service(interfaceName = "com.pinyougou.service.ItemSearchService")
public class ItemsearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 商品查询的方法
     *
     * @param searchParamMap
     */
    @Override
    public Map<String,Object> search(Map<String, Object> searchParamMap) {
        Map<String, Object> data = new HashMap<>(16);
//        创建查询对象
        Query query = new SimpleQuery("*:*");
//        获取搜索关键字
        String keyword = (String) searchParamMap.get("keyword");
//        判断keyword关键字是否为空
        if (StringUtils.isNoneBlank(keyword)){
//            创建条件对象
            Criteria criteria  = new Criteria("keywords").is(keyword);
//            添加条件到查询
            query.addCriteria(criteria);
        }
//        查询索引库(分页检索)
        ScoredPage<SolrItem> scoredPage = solrTemplate.queryForPage(query, SolrItem.class);
//        获取查询内容
        List<SolrItem> content = scoredPage.getContent();
        data.put("rows", content);
        return data;
    }
}
