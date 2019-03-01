package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.mapper.GoodsMapper;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    /**
     * 添加商品基本信息
     *
     * @param goods
     */
    @Override
        public void save(Goods goods) {

        try{
            //往tb_goods表中插入数据
            goods.setAuditStatus("0");
            goodsMapper.insertSelective(goods);
            //往tb_goods_desc表中插入数据
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());
            //往tb_item表中插入数据
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }


    }
}
