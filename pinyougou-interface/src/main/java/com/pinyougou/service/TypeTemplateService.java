package com.pinyougou.service;

import com.pinyougou.pojo.TypeTemplate;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 模板管理服务接口
 * @author whister
 */
public interface TypeTemplateService {

    /**
     * 带条件分页查询
     * @param typeTemplate
     * @param page
     * @param rows
     * @return
     */
    PageResult findByPage(TypeTemplate typeTemplate, Integer page, Integer rows);


    /**
     * 模板保存
     * @param typeTemplate
     */
    void save(TypeTemplate typeTemplate);

    /**
     * 模板更新
     * @param typeTemplate
     */
    void update(TypeTemplate typeTemplate);

    /**
     * 删除模板方法
     * @param ids
     */
    void deleteAll(long[] ids);
}
