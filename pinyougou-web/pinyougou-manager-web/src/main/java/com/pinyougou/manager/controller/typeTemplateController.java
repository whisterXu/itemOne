package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

import java.util.List;

/**
 * 模板管理控制器
 * @author whister.xu
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class typeTemplateController {

    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /**
     * 带条件分页查询
     * @param page
     * @param rows
     * @param typeTemplate
     * @return
     */
    @GetMapping("/findByPage")
    public PageResult findByPage(Integer page, Integer rows , TypeTemplate typeTemplate){
        try{
//            get请求字符编码转码
            if (typeTemplate != null && StringUtils.isNoneBlank(typeTemplate.getName())){
                typeTemplate.setName(new String(typeTemplate.getName().getBytes("ISO8859-1"), "UTF-8"));
            }
//            调用服务接口方法
            return typeTemplateService.findByPage(typeTemplate,page,rows);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/save")
    public boolean save(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.save(typeTemplate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 模板更新的方法
     * @param typeTemplate
     * @return
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.update(typeTemplate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除模板方法
     * @param ids
     * @return boolean
     */
    @GetMapping("/delete")
    public boolean delete(long[] ids){
        try{
            typeTemplateService.deleteAll(ids);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @GetMapping("/findTypeTemplateList")
    public List<TypeTemplate> findTypeTemplateList(){
        try {
            return  typeTemplateService.findTypeTemplateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
