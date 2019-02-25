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
}
