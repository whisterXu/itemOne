package com.pinyougou.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.ItemSearchService;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * 消息监听器类
 * 接收消息删除索引库索引
 * @author whister
 */
public class ItemDeleteMessageListener implements SessionAwareMessageListener<ObjectMessage> {

    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        System.out.println("++++++++++++++++++ItemDeleteMessageListener+++++++++++++++++");
        Long[] ids = (Long[]) objectMessage.getObject();
        System.out.println("ids:" + ids);
        itemSearchService.deleteSolrDoc(ids);
    }
}
