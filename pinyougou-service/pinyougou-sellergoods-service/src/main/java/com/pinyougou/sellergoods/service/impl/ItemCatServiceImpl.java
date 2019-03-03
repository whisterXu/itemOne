package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;

/**
 * @author whister
 */
@Service(interfaceName = "com.pinyougou.service.ItemCatService")
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;
    /**
     * 分页查询
     * @param total
     * @param rows
     * @return PageResult
     */
    @Override
    public PageResult findByPage(Long parentId,Integer total, Integer rows) {
        try {

            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);

            PageInfo<ItemCat> pageInfo = PageHelper.startPage(total, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    itemCatMapper.select(itemCat);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加方法
     */
    @Override
    public void save(ItemCat itemCat){
        try {
            itemCatMapper.insertSelective(itemCat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemCat itemCat) {
        try {
            itemCatMapper.updateByPrimaryKeySelective(itemCat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除分类
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        try {
            itemCatMapper.deleteByCondition(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  根据parentId 查询
     * @param parentId
     * @return List<ItemCat>
     */
    @Override
    public List<ItemCat> findItemCatByParentId(Long parentId) {
        try{
            List<ItemCat> itemCatList = itemCatMapper.findItemCatByParentId(parentId);
            return itemCatList;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
