package com.fskroes.ipwrc.dao;

import com.fskroes.ipwrc.model.ProductModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductDAO extends AbstractDAO<ProductModel> {
    private List<ProductModel> productCache;
    private SessionFactory sessionFactory;

    @Inject
    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
        productCache = new ArrayList<>();
    }

    public List getProductModels() {
        return list(namedQuery("Product.FIND_ALL"));
    }

    public ProductModel getProductByName(String name) {
        Optional<ProductModel> result = productCache.stream()
                .filter(product -> product.getName().equals(name))
                .findAny();
        return result.orElse(uniqueResult(namedQuery("Product.FIND_BY_NAME").setParameter("name", name)));
    }

    public ProductModel getProductById(int id) {
        Optional<ProductModel> result = productCache.stream()
                .filter(product -> product.getId() == id)
                .findAny();
        return result.orElse(uniqueResult(namedQuery("Product.FIND_BY_ID").setParameter("id", id)));
    }

    public ProductModel createProduct(ProductModel productModel) {
        if (sessionFactory.getCurrentSession() != null) {
            ProductModel model = (ProductModel) sessionFactory.getCurrentSession().merge(productModel);
            return model;
        }
        else {
            return (ProductModel) sessionFactory.openSession().merge(productModel);
        }
    }

    public ProductModel updateProduct(ProductModel productModel) {
        if (sessionFactory.getCurrentSession() != null) {
            ProductModel model = (ProductModel) sessionFactory.getCurrentSession().merge(productModel);
            return model;
        }
        else {
            return (ProductModel) sessionFactory.openSession().merge(productModel);
        }
    }

    public boolean deleteProduct(int id) {
        ProductModel model = getProductById(id);

        if (sessionFactory.getCurrentSession() != null) {
            sessionFactory.getCurrentSession().delete(model);
            return true;
        } else {
            return false;
        }
    }
}
