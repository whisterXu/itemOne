package com.pinyougou.service;

import com.pinyougou.pojo.User;
/**
 * UserService 用户服务接口
 * @author whistler
 * @date 2019-02-28 20:43:34
 * @version 1.0
 */
public interface UserService {

	/**
	 *  添加方法
	 * @param user  用户对象封装
	 */
	void save(User user);
	/**
	 * 发送短信
	 * @param phone 手机号码
	 * @return true或false
	 */
    boolean sendCode(String phone);

	/**
	 *  验证短信验证码是否相等的方法
	 *
	 * @param phone  手机号码
	 * @param smsCode  验证码
	 * @return  返回true或者false
	 */
	boolean checkCode(String phone,String smsCode);
}