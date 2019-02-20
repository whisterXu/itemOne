package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pinyougou.conmmon.pojo.PageResult;

/**
 * 运营商后台控制器(服务消费者)
 * @author whister
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private BrandService brandService;

    /** 分页查询全部品牌 */
    @GetMapping("/findByPage")
    public PageResult findByPage(Brand brand,Integer page, Integer rows){
        if (brand!=null && StringUtils.isNoneBlank(brand.getName())){
            try{
                brand.setName(new String(brand.getName().getBytes("ISO8859-1"), "UTF-8"));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return brandService.findByPage(brand,page,rows);
    }

    /**
     * 保存品牌
     * @param brand
     * @return boolean
     */
    @PostMapping("/save")
    public boolean save(@RequestBody Brand brand){
        try {
            brandService.save(brand);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 更新品牌
     * @param brand
     * @return boolean
     */
    @PostMapping("/update")
    public boolean update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * 批量删除
     * @param ids
     * @return boolean
     */

    @GetMapping("/delete")
    public boolean deleteAll(Long[] ids){
        try{
            brandService.deleteAll(ids);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
