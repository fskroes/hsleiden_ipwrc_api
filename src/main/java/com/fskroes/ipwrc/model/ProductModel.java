package com.fskroes.ipwrc.model;

import javax.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        ),
        @NamedNativeQuery(
                name = "Product.FIND_BY_ID",
                query = "SELECT * FROM products WHERE id = :id",
                resultClass = ProductModel.class
        )
})
public class ProductModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("usd_value")
    private double usd_value;

    @JsonProperty("category")
    private String category;

    @JsonProperty("imageurl")
    private String imageurl;

    public int getId() {
        return id;
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

    public String getImageurl() {
        return imageurl;
    }

    public void setId(int id) {
        this.id = id;
    }
}
