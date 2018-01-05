package com.fskroes.ipwrc.route;

import com.fskroes.ipwrc.model.ProductModel;
import com.fskroes.ipwrc.service.ProductService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Singleton
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductRoute {

    private ProductService productService;

    @Inject
    public ProductRoute(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @UnitOfWork
    @Path("/products")
    public Collection getProducts() {
        return productService.getAllProducts();
    }

    @GET
    @UnitOfWork
    @Path("/{name}")
    public ProductModel getProductByName(@PathParam("name") String name) {
        if (productService.getProductByName(name).isPresent()) {
            return productService.getProductByName(name).get();
        } else {
            return null;
        }
    }
}
