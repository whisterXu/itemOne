package com.pinyougou.service;

import com.pinyougou.pojo.Content;
import pinyougou.conmmon.pojo.PageResult;

import java.io.Serializable;
import java.util.List;

/**
 * ContentService 服务接口
 * @author whister
 * @date 2019-02-28 20:43:34
 * @version 1.0
 */
public interface ContentService {

	/** 添加方法 */
	void save(Content content);

	/** 修改方法 */
	void update(Content content);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Content findOne(Serializable id);

	/**
	 *  查询全部category
	 * @retur List<Content>
	 */
	List<Content> findAll();

	/**
	 *  分页查询
	 * @param page
	 * @param rows
	 * @return PageResult
	 */
	PageResult findByPage(Integer page, Integer rows);

}