package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 规格服务提供者
 *
 * @author whister.xu
 */
@Service(interfaceName="com.pinyougou.service.SpecificationService")
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    /** 注入接口的实现代理对象 */
    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 带条件分页查询
     *
     * @param specification
     * @param currentPage
     * @param rows
     * @return PageResult
     */
    @Override
    public PageResult findByPage(Specification specification, Integer currentPage, Integer rows) {
        PageInfo<Specification> PageInfo = PageHelper.startPage(currentPage, rows).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                specificationMapper.findByCondition(specification);
            }
        });
        return new PageResult(PageInfo.getTotal(),PageInfo.getList());
    }
}
