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


    /**
     * 根据模板specId查询规格选项
     * 返回lList<SpecificationOption>用来组装 [{"id":33,"text":"电视屏幕尺寸"}]  数据库保存的格式
     * @param specificationOption
     * @return List<SpecificationOption>
     */
    @Override
    public List<SpecificationOption> select(SpecificationOption specificationOption) {
        try{
            return specificationOptionMapper.select(specificationOption);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
