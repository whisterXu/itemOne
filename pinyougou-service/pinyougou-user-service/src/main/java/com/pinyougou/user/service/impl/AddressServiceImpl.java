package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.AddressMapper;
import com.pinyougou.pojo.Address;
import com.pinyougou.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @description: AddressServiceImpl
 * -------------------------------
 * @author： Whistler.Xu
 * @date： Created in 2019/3/17
 * @time: 17:29 星期日
 * @vision: 1.0
 * --------------------------------
 */
@Service(interfaceName = "com.pinyougou.service.AddressService")
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    /**
     * 添加方法
     *
     * @param address
     */
    @Override
    public void save(Address address) {

    }

    /**
     * 修改方法
     *
     * @param address
     */
    @Override
    public void update(Address address) {

    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {

    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {

    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Address findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部用户地址
     *
     * @return 返回用户地址集合
     */
    @Override
    public List<Address> findAll() {
        try{
            Example example = new Example(Address.class);
            example.orderBy("isDefault").desc();

            return addressMapper.selectByExample(example);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 多条件分页查询
     *
     * @param address
     * @param page
     * @param rows
     */
    @Override
    public List<Address> findByPage(Address address, int page, int rows) {
        return null;
    }
}
