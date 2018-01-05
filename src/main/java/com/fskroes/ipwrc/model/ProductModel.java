package com.fskroes.ipwrc.model;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "products")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Product.FIND_ALL",
                query = "SELECT * FROM products",
                resultClass = ProductModel.class
        ),
        @NamedNativeQuery(
                name = "Product.FIND_BY_NAME",
                query = "SELECT * FROM products WHERE name = :name",
                resultClass = ProductModel.class
        )
})
public class ProductModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name")
    private String name;

    private double usd_value;
    private String category;

    public void setName(String name) {
        this.name = name;
    }

    public void setUsd_value(double usd_value) {
        this.usd_value = usd_value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getUsd_value() {
        return usd_value;
    }

    public String getCategory() {
        return category;
    }
}
