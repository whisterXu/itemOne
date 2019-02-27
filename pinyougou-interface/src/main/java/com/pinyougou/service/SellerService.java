package com.pinyougou.service;

import com.pinyougou.pojo.Seller;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 单家服务接口
 * @author whister
 */
public interface SellerService {
    /**
     * 带条件分页查询
     * @param currentPage
     * @param itemsPerPage
     * @param seller
     * @return pageResult
     */
    public PageResult findByPage(Integer currentPage, Integer itemsPerPage, Seller seller);

    /**
     * 更新审核状态码
     * @param seller
     */
    void updateStatus(Seller seller);
}
