package com.pinyougou.mapper;

import com.pinyougou.pojo.Seller;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SellerMapper extends Mapper<Seller> {

    List<Seller> findByCondition(Seller seller);

    /**
     * 根据用户ID修改状态码
     * @param seller
     */
    @Update("update tb_seller set status = #{status} where seller_id = #{sellerId};")
    void updateStatusBySellerId(Seller seller);
}
