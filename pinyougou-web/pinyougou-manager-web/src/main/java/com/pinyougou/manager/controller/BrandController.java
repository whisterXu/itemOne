package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 运营商后台控制器(服务消费者)
 * @author whister
 */
@RestController
public class BrandController {
    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private BrandService brandService;

    /** 查询全部品牌 */
    @GetMapping("/brand/findAll")
    public List<Brand> findAll(){
        List<Brand> brandList = brandService.findAll();
        return brandList;
    }
}
