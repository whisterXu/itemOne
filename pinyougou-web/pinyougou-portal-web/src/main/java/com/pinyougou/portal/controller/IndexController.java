package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Reference(timeout = 10000)
    private ContentService contentService;

    /**
     * 通过CategoryId查询Category数据
     * @param categoryId
     * @return List<Content>
     */
    @GetMapping("/findCategoryByCategoryId")
    public List<Content> findCategoryByCategoryId(Long categoryId){
        try{
            return contentService.findCategoryByCategoryId(categoryId);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
