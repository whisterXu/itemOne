package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.SpecificationOptionService;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 模板管理控制器
 * @author whister.xu
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;
    @Reference(timeout = 10000)
    private SpecificationOptionService specificationOptionService;

    /**
     *  根据typeTemplate 主键查询品牌
     * @param id
     * @return TypeTemplate
     */
    @GetMapping("/findOne")
    public TypeTemplate findBrandById(Long id){
        try {
            return typeTemplateService.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/findSpecByTypeTemplateId")
    public List<Map> findSpecByTypeTemplateId(Long id){
// 前端需要的格式:[{"id":27,"text":"网络",options:[{},{}]},{"id":32,"text":"机身内存",options:[{},{}]}]
        try {
            // 根据模板主键ID 查询模板
            TypeTemplate typeTemplate = typeTemplateService.findOne(id);
            /**
             * [{"id":33,"text":"电视屏幕尺寸"}]  数据库保存的格式
             * 获取模版中所有的规格，转化成  List<Map>
             */
            List<Map> specList = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
            for (Map map : specList) {
                //创建条件查询对象
                SpecificationOption specificationOption = new SpecificationOption();
                specificationOption.setSpecId(Long.valueOf(map.get("id").toString()));
                //根据
                List<SpecificationOption> specOptions = specificationOptionService.select(specificationOption);
                map.put("options",specOptions);
            }
            return  specList;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
