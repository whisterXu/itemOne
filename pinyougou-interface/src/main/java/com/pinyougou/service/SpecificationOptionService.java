package com.pinyougou.service;

import com.pinyougou.pojo.SpecificationOption;

import java.util.List;

/**
 * SpecificationOptionService规格选项服务接口
 * @author whister
 */
public interface SpecificationOptionService {

    List<SpecificationOption> findBySpecId(Long id);
}
