package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import pinyougou.conmmon.pojo.PageResult;

import java.io.Serializable;
import java.util.Date;

@Service(interfaceName = "com.pinyougou.service.SellerService")
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;
    /**
     * 带条件分页查询
     *
     * @param currentPage
     * @param itemsPerPage
     * @param seller
     * @return pageResult
     */
    @Override
    public PageResult findByPage(Integer currentPage, Integer itemsPerPage, Seller seller) {
        try {
            PageInfo<PageResult> pageInfo = PageHelper.startPage(currentPage, itemsPerPage).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    sellerMapper.findByCondition(seller);
                }
            });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新审核状态码
     * @param seller
     */
    @Override
    public void updateStatus(Seller seller) {
        try{
            sellerMapper.updateStatusBySellerId(seller);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 商家注册信息保存
     * @param seller
     */
    @Override
    public void save(Seller seller) {
        try{
            seller.setStatus("0");
            seller.setCreateTime(new Date());
            sellerMapper.insertSelective(seller);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 根据ID查询商家信息
     *
     * @param id
     * @return
     */
    @Override
    public Seller findOne(Serializable id) {
        try{
            return  sellerMapper.selectByPrimaryKey(id);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
