package com.fskroes.ipwrc.route;

import com.fskroes.ipwrc.model.ProductModel;
import com.fskroes.ipwrc.service.ProductService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
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

//    @GET
//    @UnitOfWork
//    @Path("/{name}")
//    public ProductModel getProductByName(@PathParam("name") String name) {
//        if (productService.getProductByName(name).isPresent()) {
//            return productService.getProductByName(name).get();
//        } else {
//            return null;
//        }
//    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public ProductModel getProductById(@PathParam("id") int id) {
        if (productService.getProductById(id).isPresent()) {
            return productService.getProductById(id).get();
        } else {
            return null;
        }
    }


    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/new")
    public ProductModel insertProduct(ProductModel model) {
        System.out.println("-----");
        System.out.println("INSIDE POST METHOD -- PRODUCT");
        System.out.println("-----");
        return productService.createProduct(model);
    }

    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/edit/{id}")
    public ProductModel insertProduct(@PathParam("id") int id, ProductModel model) {
        System.out.println("-----");
        System.out.println("INSIDE PUT METHOD -- PRODUCT");
        System.out.println("-----");
        return productService.updateProduct(id, model);
    }

    @DELETE
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public boolean deleteProduct(@PathParam("id") int id) {
        System.out.println("-----");
        System.out.println("INSIDE DELETE METHOD -- PRODUCT");
        System.out.println("-----");
        return productService.deleteProduct(id);
    }
}
