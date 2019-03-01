package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;

@Service(interfaceName ="com.pinyougou.service.TypeTemplateService" )
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {


    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    /**
     * 带条件分页查询
     *
     * @param typeTemplate
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult findByPage(TypeTemplate typeTemplate, Integer page, Integer rows) {

        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    typeTemplateMapper.findByCondition(typeTemplate);
                }
            });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存模板的方法
     * @param typeTemplate
     */
    @Override
    public void save(TypeTemplate typeTemplate) {
        try {
            typeTemplateMapper.insertSelective(typeTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 模板更新
     * @param typeTemplate
     */
    @Override
    public void update(TypeTemplate typeTemplate) {
        try {
            typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除模板方法
     * @param ids
     */
    @Override
    public void deleteAll(long[] ids) {
        typeTemplateMapper.deleteAll(ids);
    }

    /**
     * 查询全部模板列表
     * @return
     */
    @Override
    public List<TypeTemplate> findTypeTemplateList() {
        try {
            return typeTemplateMapper.selectAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据typeTemplate id主键查询品牌
     * @param id
     * @return TypeTemplate
     */
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

}
