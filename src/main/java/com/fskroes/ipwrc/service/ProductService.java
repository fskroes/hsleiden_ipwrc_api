package com.fskroes.ipwrc.service;

import com.fskroes.ipwrc.dao.ProductDAO;
import com.fskroes.ipwrc.model.ProductModel;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class ProductService {

    private ProductDAO productDAO;

    @Inject
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Collection getAllProducts() {
        return this.productDAO.getProductModels();
    }

    public Optional<ProductModel> getProductByName(String name) {
        return Optional.ofNullable(productDAO.getProductByName(name));
    }
}
