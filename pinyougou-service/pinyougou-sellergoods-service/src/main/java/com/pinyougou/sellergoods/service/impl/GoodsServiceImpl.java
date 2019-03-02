package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.pojo.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 添加商品基本信息
     *
     * @param goods
     */
    @Override
    public void save(Goods goods) {

        try {
            //往tb_goods表中插入数据
            goods.setAuditStatus("0");
            goodsMapper.insertSelective(goods);
            //往tb_goods_desc表中插入数据
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());

//              往tb_item表中插入数据
//            {spec:{"网络":"联通4G","机身内存":"64G"},price:0,num:0,status:'0',isDefault:'0'}

//            插入标题 : 格式SPU + SKU Apple iPhone 8 Plus (A1864) 联通4G 64G
            for (Item item : goods.getItems()) {
                //创建字符串StringBuilder  实现定义SKU商品的标题拼接
                StringBuilder title = new StringBuilder(goods.getGoodsName());
//                把规格选项JSON字符串转化成Map集合
                Map<String, Object> spec = JSON.parseObject(item.getSpec());
//                迭代map集合的value数据
                for (Object value : spec.values()) {
//                    拼接字符串,
                    title.append(" " + value);
                }
//                形成标题格式:Apple iPhone 8 Plus (A1864) 联通4G 64G
                item.setTitle(title.toString());

//                设置SKU商品图片地址
                String itemImages = goods.getGoodsDesc().getItemImages();
                List<Map> imageList = JSON.parseArray(itemImages, Map.class);
                if (imageList != null && imageList.size() > 0) {
                    item.setImage((String) imageList.get(0).get("url"));
                }

//                设置库存数
                item.setNum(999);
//                设置状态
                item.setStatus("1");
//                设置三级分类ID
                item.setCategoryid(goods.getCategory3Id());
//                设置创建日期
                item.setCreateTime(new Date());
//                修改日期
                item.setUpdateTime(new Date());
//                设置SPU商品编号
                item.setGoodsId(goods.getId());
//                设置商家id编号
                item.setSellerId(goods.getSellerId());

//                设置三级分类名称: 通过goods中三级分类ID查询itemCat获得分类名称
                ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
                item.setCategory(itemCat != null ? itemCat.getName(): "");
//                设置品牌名称:通过goods中品牌ID查询brand获得品牌名称
                Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
                item.setBrand(brand != null ? brand.getName() : "");
//                设置店铺名称:通过goods中商家ID查询seller获得店铺名称
                Seller seller = sellerMapper.selectByPrimaryKey(goods.getSellerId());
                item.setSeller(seller != null ? seller.getNickName() : "");

//                 添加到数据库表中
                itemMapper.insertSelective(item);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateStatus(Long[] ids,String auditStatus,String columnName) {
        goodsMapper.updateStatus(ids,auditStatus,columnName);
    }

    /**
     *  带条件分页查询
     * @param goods
     * @param page
     * @param rows
     * @return PageResult
     */
    @Override
    public PageResult findByPage(Goods goods, Integer page, Integer rows) {
        try {
            PageInfo<Map> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    goodsMapper.findByCondition(goods);
                }
            });
            List<Map> goodsList = pageInfo.getList();
            for (Map map : goodsList) {

    //            查询一级分类的名称
                Long category1Id = (Long) map.get("category1Id");
                ItemCat itemCat1 = itemCatMapper.selectByPrimaryKey(category1Id);
                map.put("category1Name",itemCat1 != null ? itemCat1.getName() : "");

    //            查询二级分类的名称
                Long category2Id = (Long) map.get("category2Id");
                ItemCat itemCat2 = itemCatMapper.selectByPrimaryKey(category2Id);
                map.put("category2Name",itemCat2 != null ? itemCat2.getName() : "");

    //            查询三级分类的名称
                Long category3Id = (Long) map.get("category3Id");
                ItemCat itemCat3 = itemCatMapper.selectByPrimaryKey(category3Id);
                map.put("category3Name",itemCat3 != null ? itemCat3.getName() : "");
            }
            return new PageResult(pageInfo.getTotal(),goodsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
