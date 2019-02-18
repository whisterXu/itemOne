package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.service.BrandService;
import com.pinyougou.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 * @author whister
 */
@Service(interfaceName="com.pinyougou.service.BrandService")
@Transactional
public class BranServiceImpl implements BrandService {

    /**
     * 查询全部brand方法
     * 注入数据访问接口代理对象
     */
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
