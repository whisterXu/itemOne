package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 规格服务提供者
 *
 * @author whister.xu
 */
@Service(interfaceName="com.pinyougou.service.SpecificationOptionService")
@Transactional
public class SpecificationOptionServiceImpl implements SpecificationOptionService {

    /** 注入接口的实现代理对象 */
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     *  根据ID查询SpecificationOption
     * @param id
     * @return
     */
    @Override
    public List<SpecificationOption> findBySpecId(Long id) {
        SpecificationOption so = new SpecificationOption();
        so.setSpecId(id);
        return specificationOptionMapper.select(so);
    }
}
