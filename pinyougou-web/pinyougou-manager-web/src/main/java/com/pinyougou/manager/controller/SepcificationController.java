package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Specification;
import com.pinyougou.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

import java.io.Serializable;

/**
 * 运营商后台规格控制器(服务消费者)
 * @author whister.xu
 */
@RestController
@RequestMapping("/specification")
public class SepcificationController {

    @Reference(timeout = 10000)
    private SpecificationService specificationService;

    /**
     * 带条件分页查询
     *
     * @param specification
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/findByPage")
    public PageResult findByPage(Specification specification, Integer page, Integer rows) {

            if (specification != null && StringUtils.isNoneBlank(specification.getSpecName())) {
                try {
                    specification.setSpecName(new String(specification.getSpecName().getBytes("ISO8859-1"), "UTF-8"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        return specificationService.findByPage(specification, page, rows);
    }

    /**
     * 保存的控制器
     * @param specification
     * @return
     */
    @PostMapping("/save")
    public boolean save(@RequestBody Specification specification){
        System.out.println("specification="+specification);
        try {
            specificationService.save(specification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/delete")
    public boolean delete(Serializable[] ids){
        try{
            specificationService.deleteAll(ids);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}