package com.shulei.ssm.service;

import com.shulei.ssm.domain.Product;

import java.util.List;

public interface IProductService {
    public void save(Product product) throws Exception;
    public List<Product> findAlll() throws Exception;
}
