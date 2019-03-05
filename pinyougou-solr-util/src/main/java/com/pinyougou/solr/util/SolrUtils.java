package com.pinyougou.solr.util;
import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.solr.SolrItem;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  将数据库中的数据导入到solr索引库中
 * @author whister
 */
@Component
public class SolrUtils {

    /**
     * 注入itemMapper 和 solrTemplate
     */
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;

    public void importItemData(){

        Item item = new Item();
        //状态正常的商品
        item.setStatus("1");
//        从数据库中表中查询数据
        List<Item> itemList = itemMapper.selectAll();
//        创建list集合用于存放solrItem对象
        System.out.println("================商品列表================");
        ArrayList<Object> solrItemList = new ArrayList<>();
        for (Item item1 : itemList) {
            System.out.println("副标题:" + item1.getTitle());

//            solr索引库对应的实体类 ,属性对应域
            SolrItem solrItem = new SolrItem();
            solrItem.setId(item1.getId());
            solrItem.setTitle(item1.getTitle());
            solrItem.setPrice(item1.getPrice());
            solrItem.setImage(item1.getImage());
            solrItem.setGoodsId(item1.getGoodsId());
            solrItem.setCategory(item1.getCategory());
            solrItem.setBrand(item1.getBrand());
            solrItem.setSeller(item1.getSeller());
            solrItem.setUpdateTime(item1.getUpdateTime());

//           把json字符串转化成map集合
            Map map = JSON.parseObject(item1.getSpec(), Map.class);
//            设置动态域
            solrItem.setSpecMap(map);
//            添加到list集合中
            solrItemList.add(solrItem);
        }
//        调用solrTemplate的saveBeans()方法批量保存
        UpdateResponse updateResponse = solrTemplate.saveBeans(solrItemList);

//        判断响应状态码是否为0
        if (updateResponse.getStatus() == 0){
//            提交
            solrTemplate.commit();
        }
        else {
//            回滚
            solrTemplate.rollback();
        }
        System.out.println("==============结束=================");
    }
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SolrUtils solrUtils = applicationContext.getBean(SolrUtils.class);
        solrUtils.importItemData();
    }
}
