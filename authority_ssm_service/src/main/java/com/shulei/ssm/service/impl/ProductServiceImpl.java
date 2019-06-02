package com.shulei.ssm.service.impl;

import com.shulei.ssm.dao.IProductDao;
import com.shulei.ssm.domain.Product;
import com.shulei.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{
    @Autowired
    private IProductDao productDao;

    @Override
    public void save(Product product) throws Exception{
        productDao.save(product);
    }

    @Override
    public List<Product> findAlll() throws Exception {
        return productDao.findAll();
    }
}
