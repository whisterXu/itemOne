package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import pinyougou.conmmon.pojo.PageResult;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
/**
 * ContentServiceImpl 服务接口实现类
 * @author whister
 * @date 2018-08-14 00:23:07
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.ContentService")
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private RedisTemplate redisTemplate;

	/** 添加方法 */
	@Override
	public void save(Content content){
		try {
			contentMapper.insertSelective(content);
			//清空redis
			redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	@Override
	public void update(Content content){
		try {
			contentMapper.updateByPrimaryKeySelective(content);
            //清空redis
            redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	@Override
	public void delete(Serializable id){
		try {
			contentMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	@Override
	public void deleteAll(Serializable[] ids){
		try {
			// 创建示范对象
			Example example = new Example(Content.class);
			// 创建条件对象
			Example.Criteria criteria = example.createCriteria();
			// 创建In条件
			criteria.andIn("id", Arrays.asList(ids));
			// 根据示范对象删除
			contentMapper.deleteByExample(example);
            //清空redis
            redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	@Override
	public Content findOne(Serializable id){
		try {
			return contentMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	@Override
	public List<Content> findAll(){
		try {
			return contentMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 多条件分页查询
	 * @param page
	 * @param rows
	 * @return PageResult
	 */
	@Override
	public PageResult findByPage(Integer page, Integer rows){
		try {
			PageInfo<Content> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
				@Override
				public void doSelect() {
					contentMapper.selectAll();
				}
			});
			return new PageResult(pageInfo.getTotal(), pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 通过CategoryId查询广告内容显示在在首页
	 * @param categoryId
	 * @return  List<Content>
	 */
    @Override
    public List<Content> findCategoryByCategoryId(Long categoryId) {
		//定义广告数据
		List<Content> contentList = null;
		//从Redis中读取数据
		contentList = (List<Content>) redisTemplate.boundValueOps("content").get();
		if (contentList != null && contentList.size() > 0){
			System.out.println("=============从redis中读取数据===========");
			return contentList;
		}

		Example example = new Example(Content.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status",categoryId);
		example.orderBy("sortOrder").asc();

		//查询广告数据
		contentList = contentMapper.selectByExample(example);
		System.out.println("=============从mysql中读取数据===========");
		//存入redis
		redisTemplate.boundValueOps("content").set(contentList);
		return contentList;
	}

}