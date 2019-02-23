package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

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
}
