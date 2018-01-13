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

    public Optional<ProductModel> getProductById(int id) {
        return Optional.ofNullable(productDAO.getProductById(id));
    }

    public ProductModel createProduct(ProductModel productModel) {
        return productDAO.createProduct(productModel);
    }

    public ProductModel updateProduct(int id, ProductModel productModel) {
//        ProductModel oldEmployee = productDAO.getProductById(id);
        productModel.setId(id);
        return productDAO.updateProduct(productModel);
    }

    public boolean deleteProduct(int id) {
        return productDAO.deleteProduct(id);
    }
}
