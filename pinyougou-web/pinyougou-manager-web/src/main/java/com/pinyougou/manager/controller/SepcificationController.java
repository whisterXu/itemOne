package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import com.pinyougou.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 运营商后台规格控制器(服务消费者)
 * @author whister.xu
 */
@RestController
@RequestMapping("/specification")
public class SepcificationController {

    @Reference(timeout = 10000)
    private SpecificationService specificationService;
    @Reference(timeout = 10000)
    private SpecificationOptionService specificationOptionService;

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
        try {
            specificationService.save(specification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  更新品牌名称和规格选项
     * @param specification
     * @return boolean
     */
    @PostMapping("/update")
    public boolean update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** 批量删除 */
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try{
            specificationService.deleteAll(ids);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 根据SpecId查询SpecificationOption
     * @param id
     * @return List<SpecificationOption>
     */
    @GetMapping("/findBySpecId")
    public List<SpecificationOption> findBySpecId(Long id){
        try {
            return specificationOptionService.findBySpecId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询全部规格 返回给模板管理的增加选项
     * 格式:{data: [{id: 1, text: '联想'}, {id: 2, text: '华为'}, {id: 3, text: '小米'}]};
     * @return   List<Map<String ,Object>>
     */
    @GetMapping("/findSpecificationList")
    public List<Map<String ,Object>> findSpecificationList(){
        try {
            return specificationService.findSpecificationList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}