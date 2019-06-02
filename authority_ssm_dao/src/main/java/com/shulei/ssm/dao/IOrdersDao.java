package com.shulei.ssm.dao;

import com.shulei.ssm.domain.Orders;
import com.shulei.ssm.domain.Product;
import org.apache.ibatis.annotations.*;

import java.lang.reflect.Member;
import java.util.List;

public interface IOrdersDao {
    @Select("select * from orders")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",one = @One(select =
                    "com.shulei.ssm.dao.IProductDao.findById"))
    })
    public List<Orders> findAll() throws Exception;


    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",
                    javaType = Product.class,one = @One(select =
                    "com.shulei.ssm.dao.IProductDao.findById")),
            @Result(column = "memberId",property = "member",
                    javaType = Member.class,one = @One(select =
                    "com.shulei.ssm.dao.IMemberDao.findById")),
            @Result(column = "id",property = "travellers",
                    javaType = List.class,many = @Many(select =
                    "com.shulei.ssm.dao.ITravellerDao.findByOrdersId")),

    })
    public Orders findById(String odersId) throws Exception;
}
