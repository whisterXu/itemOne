package com.pinyougou.service;

import com.pinyougou.pojo.SpecificationOption;

import java.util.List;

/**
 * SpecificationOptionService规格选项服务接口
 * @author whister
 */
public interface SpecificationOptionService {

    /**
     * 通过SpecId查询规格选项
     * @param id
     * @return
     */
    List<SpecificationOption> findBySpecId(Long id);

    /**
     * 根据模板specId查询规格选项
     * @param specificationOption
     * @return List<SpecificationOption>
     */
    List<SpecificationOption> select(SpecificationOption specificationOption);
}
