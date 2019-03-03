package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

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
        PageInfo<Specification> pageInfo = PageHelper.startPage(currentPage, rows).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                specificationMapper.findByCondition(specification);
            }
        });
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 保存的方法
     * @param specification
     */
    @Override
    public void save(Specification specification) {
        try{
            specificationMapper.insertSelective(specification);
            specificationOptionMapper.save(specification);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 修改规格和规格选项的方法
     * @param specification
     */
    @Override
    public void update(Specification specification) {

        try {
            //根据主键,修改规格表中的规格名称.
            specificationMapper.updateByPrimaryKey(specification);

//        先根据specId删除规格选项中的内容
            SpecificationOption specificationOption = new SpecificationOption();
            specificationOption.setSpecId(specification.getId());
            specificationOptionMapper.delete(specificationOption);

//        再添加规格选项内容.
            specificationOptionMapper.save(specification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  批量删除
     * @param ids
     */
    @Override
    public void deleteAll(Long[] ids) {
        try{
            specificationMapper.deleteAll(ids);
            specificationOptionMapper.deleteAll(ids);
        }catch(Exception ex){
            throw new RuntimeException();
        }
    }

    /**
     * 查询全部规格 返回给模板管理的增加选项
     * 格式:{data: [{id: 1, text: '联想'}, {id: 2, text: '华为'}, {id: 3, text: '小米'}]};
     *
     * @return List<Map < String , Object>>
     */
    @Override
    public List<Map<String, Object>> findSpecificationList() {
        return specificationMapper.findSpecificationList();
    }
}
