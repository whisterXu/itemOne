package com.pinyougou.item.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.GoodsService;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @description: 消息监听器(生成静态html页面)
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/12
 * @time: 21:31 星期二
 * @vision: 1.0
 * --------------------------------
 */
public class PageMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Value("${page.dir}")
    private String pageDir;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        try{
            System.out.println("===============PageMessageListener===============");
            //获取消息
            String goodsId = textMessage.getText();
            //打印消息
            System.out.println("goodsId:" + goodsId);
            //根据模版文件获取模版对象
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("item.ftl");
            //获取模板数据
            Map<String, Object> dataModel = goodsService.getGoodsByGoodsId(Long.parseLong(goodsId));
            //创建输出流对象
            OutputStreamWriter writer  = new OutputStreamWriter(new FileOutputStream(pageDir + goodsId + ".html"), "UTF-8");
            //填充模板生成静态页面
            template.process(dataModel,writer);
            //关闭流
            writer.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
