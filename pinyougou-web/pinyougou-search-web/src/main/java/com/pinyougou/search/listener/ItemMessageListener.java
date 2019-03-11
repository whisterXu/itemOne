package com.pinyougou.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.Item;
import com.pinyougou.service.GoodsService;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品消息监听器
 * @author whister
 */
public class ItemMessageListener implements SessionAwareMessageListener<ObjectMessage> {

    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        System.out.println("===ItemMessageListener===");
        Long[] ids = (Long[]) objectMessage.getObject();
        System.out.println("ids:" + Arrays.toString(ids));
        //查询上架的SKU商品
        List<Item> itemList = goodsService.findItemByGoodsId(ids);
        //把 List<Item> 转化成 list<solrItem>
        if (itemList.size() > 0 && itemList != null) {
            List<SolrItem> solrItemList = new ArrayList<>(16);
            for (Item item : itemList) {
                SolrItem solrItem = new SolrItem();
                solrItem.setId(item.getId());
                //把item中的字段属性设置到solrItem对应的字段属性中
                solrItem.setSpecMap(JSON.parseObject(item.getSpec(),Map.class));
                solrItem.setTitle(item.getTitle());
                solrItem.setPrice(item.getPrice());
                solrItem.setImage(item.getImage());
                solrItem.setGoodsId(item.getGoodsId());
                solrItem.setCategory(item.getCategory());
                solrItem.setBrand(item.getBrand());
                solrItem.setSeller(item.getSeller());
                solrItem.setUpdateTime(item.getUpdateTime());

                //添加到solrItemList 集合
                solrItemList.add(solrItem);

            }

            // 把SKU商品数据同步到索引库
            itemSearchService.saveOrUpdate(solrItemList);
        }
    }
}
