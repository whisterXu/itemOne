package com.pinyougou.service;

import com.pinyougou.pojo.Order;
import com.pinyougou.pojo.PayLog;

import java.util.List;
import java.io.Serializable;
/**
 * OrderService 服务接口
 * @author whister
 * @date 2019-02-28 20:43:34
 * @version 1.0
 */
public interface OrderService {

	/** 添加方法
	 * @param order
	 */
	void save(Order order);

	/** 修改方法 */
	void update(Order order);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Order findOne(Serializable id);

	/** 查询全部 */
	List<Order> findAll();

	/** 多条件分页查询 */
	List<Order> findByPage(Order order, int page, int rows);

	/**
	 * 根据用户id查询支付日志
	 * @param username  用户id
	 * @return  返回支付日志对象封装参数
	 */
	PayLog findPayLogFromRedis(String username);
}