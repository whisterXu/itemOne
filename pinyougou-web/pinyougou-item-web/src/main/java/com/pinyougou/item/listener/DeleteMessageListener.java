package com.pinyougou.item.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.File;

/**
 * @description: DeleteMessageListener
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/13
 * @time: 10:34 星期三
 * @vision: 1.0
 * --------------------------------
 */
public class DeleteMessageListener implements SessionAwareMessageListener<ObjectMessage> {

    @Value("${page.dir}")
    private String pageDir;
    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        try {
            //获取消息内容
            Long[] goodsIds = (Long[]) objectMessage.getObject();
            System.out.println("===========DeleteMessageListener===========");
            System.out.println("goodsId" + goodsIds.toString());
            for (Long goodsId : goodsIds) {
                //创建文件对象
                File file = new File(pageDir + goodsId + ".html");
                //判断文件是否存在
                    if (file.exists()) {
                    //如果存在就删除文件
                    file.delete();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
