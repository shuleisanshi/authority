package com.shulei.ssm.service;

import com.shulei.ssm.domain.Orders;

import java.util.List;

public interface IOrdersService {
    List<Orders> findAll(int page, int size) throws Exception;

    Orders findById(String odersId) throws Exception;
}
