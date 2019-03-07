package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索服务
 * @author whister
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
    public Map<String, Object> search(Map<String, Object> searchParamMap) {
//        创建map集合存储对象
        Map<String, Object> data = new HashMap<>(16);
//        获取搜索关键字
        String keyword = (String) searchParamMap.get("keyword");

//        获得当前页参数
        Integer page = (Integer) searchParamMap.get("page");
//        获得页大小参数
        Integer rows = (Integer) searchParamMap.get("rows");
        if (page == null){
            page = 1;
        }
        if (rows == null){
            rows = 15;
        }


//        获取排序字段和排序方式参数
        String sortField = (String) searchParamMap.get("sortField");
        String sortWay = (String) searchParamMap.get("sort");

//        判断keyword关键字是否为空
        if (StringUtils.isNoneBlank(keyword)) {
//            创建高亮查询对象
            HighlightQuery highlightQuery = new SimpleHighlightQuery();
//            创建高亮查询选项
            HighlightOptions highlightOptions = new HighlightOptions();
//            设置高亮域
            highlightOptions.addField("title");
//            设置高亮前缀
            highlightOptions.setSimplePrefix("<font color='red'>");
//            设置高亮后缀
            highlightOptions.setSimplePostfix("</font>");
//            高亮对象中设置高亮选项
            highlightQuery.setHighlightOptions(highlightOptions);
//            创建条件对象
            Criteria criteria = new Criteria("keywords").is(keyword);
//            添加条件到查询对象
            highlightQuery.addCriteria(criteria);

             /* ####### 按照分类过滤 ,获取分类参数  ######## */
            String category = (String) searchParamMap.get("category");
            if (StringUtils.isNoneBlank(category)){
//                创建条件对象
                Criteria categoryCategory = new Criteria("category").is(category);
//                添加分类过滤条件
                highlightQuery.addCriteria(categoryCategory);
            }

          /* ######## 2. 按照品牌过滤 获取品牌参数####### */
            String brand = (String) searchParamMap.get("brand");
//            判断品牌是否为空
            if (StringUtils.isNoneBlank(brand)){
//                创建条件对象
                Criteria categoryBrand= new Criteria("brand").is(brand);
//                添加品牌过滤条件
                highlightQuery.addCriteria(categoryBrand);
            }

             /* ######## 3.按照规格过滤查询   ,获取规格参数map集合 ######*/
            Map<String,String> spec = (Map<String, String>) searchParamMap.get("spec");
//            判断是否为空
            if (spec != null) {
//                迭代获取key
                for (String key : spec.keySet()) {
//                    创建条件对象  , 通过key获得value
                    Criteria criteriaSpec = new Criteria("spec_" + key).is(spec.get(key));
//                    将过滤条件添加到查询对象
                    highlightQuery.addCriteria(criteriaSpec);
                }
            }

            /* ########4.按照价格过滤查询  获取价格过滤参数######## */
            String price = (String) searchParamMap.get("price");
//            创建查询条件对象
            Criteria criteriaPrice = new Criteria("price");
//            判断是否为空
            if (StringUtils.isNoneBlank(price)){
//                以-分割price":"500-1000"  得到字符串数组
                String[] split = price.split("-");
//                定义比较变量
                String minPrice = "0";
                String maxPrice = "*";
//                判断分割后第一个字符是否是0   (0-500)
                if (minPrice.equals(split[0])){
                    criteriaPrice.lessThanEqual(split[1]);
//                    判断分割最后一个字符是否是(3000 - *)
                }else if (maxPrice.equals(split[1])){
//                   条件
                    criteriaPrice.greaterThanEqual(split[0]);
//                    其他的(500-1000)
                }else {
                    criteriaPrice.between(split[0],split[1]);
                }
//               将criteriaPrice过滤条件添加到查询对象
                highlightQuery.addCriteria(criteriaPrice);
            }

//            设置起始查询数和设置页大小
            highlightQuery.setOffset((page-1) * rows);
            highlightQuery.setRows(rows);

            /* ###### 排序方式查询 ######  */
            if (StringUtils.isNoneBlank(sortField) && StringUtils.isNoneBlank(sortWay)){
//                判断排序参数是否为空后, 创建排序对象,添加参数和排序方式.
                Sort sort = new Sort("ASC".equalsIgnoreCase(sortWay) ? Sort.Direction.ASC:Sort.Direction.DESC,sortField);
//                将排序对象添加到查询对象中
                highlightQuery.addSort(sort);
            }


//            获取分页查询对象
            HighlightPage<SolrItem> hp = solrTemplate.queryForHighlightPage(highlightQuery, SolrItem.class);
            List<HighlightEntry<SolrItem>> highlighted = hp.getHighlighted();
            for (HighlightEntry<SolrItem> she : highlighted) {
                SolrItem solrItem = she.getEntity();
                if (she.getHighlights().size() > 0 && she.getHighlights().get(0).getSnipplets().size() > 0) {
//                    设置高亮的结果
                    solrItem.setTitle(she.getHighlights().get(0).getSnipplets().get(0));
                }
            }


            /* ########分页查询######## */
            long totalElements = hp.getTotalElements();
            int totalPages = hp.getTotalPages();

//            添加到map集合
            data.put("rows", hp.getContent());
            data.put("totalPages",totalPages);
            data.put("total",totalElements);

        }

        else {  //简单查询

//          创建查询对象
            Query query = new SimpleQuery("*:*");

//          设置起始查询数和页大小
            query.setOffset((page-1) * rows);
            query.setRows(rows);

//           查询索引库(分页检索)
            ScoredPage<SolrItem> scoredPage = solrTemplate.queryForPage(query,SolrItem.class);

            /* ########获取分页查询数据######## */
            List<SolrItem> content = scoredPage.getContent();
//            获取总记录数和获取总页数
            long totalElements = scoredPage.getTotalElements();
            int totalPages = scoredPage.getTotalPages();

//            添加到map集合
            data.put("rows", content);
            data.put("totalPages",totalPages);
            data.put("total",totalElements);
        }
        return data;
    }
}
