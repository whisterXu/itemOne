package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 服务实现类
 * @author whister
 */
@Service(interfaceName="com.pinyougou.service.BrandService")
@Transactional
public class BranServiceImpl implements BrandService {

    /**
     * 带条件分页查询
     * 注入数据访问接口代理对象
     */
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult findByPage(Brand brand,Integer currentPage,Integer rows) {
        // 开始分页
        PageInfo<Brand> pageInfo = PageHelper.startPage(currentPage, rows).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                brandMapper.findByCondition(brand);
            }
        });
        System.out.println("总记录数:" + pageInfo.getTotal());
        System.out.println("总页数:" + pageInfo.getPages());
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /** 添加方法 */
    @Override
    public void save(Brand brand) {
        brandMapper.insert(brand);
    }

    /** 更新方法 */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /** 批量删除方法 */
    @Override
    public void deleteAll(Long[] ids) {
        try{
            brandMapper.deleteAll(ids);
        }catch(Exception ex){
            throw new RuntimeException();
        }
    }
}
