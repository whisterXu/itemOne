package com.pinyougou.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/**
 * @description: UserDetailsServiceImpl
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/15
 * @time: 0:11 星期五
 * @vision: 1.0
 * --------------------------------
 */
public class UserDetailServiceImpl implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("用户名(username):" + username);
        //创建集合封装角色
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        //添加ROLE_USER橘色
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //返回用户
        return new User(username,"",authorities);
    }
}
