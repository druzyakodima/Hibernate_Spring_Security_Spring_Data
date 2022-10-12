package com.example.lesson7_spring_data.service;

import com.example.lesson7_spring_data.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProductRepr {

    private Long id;

    @Nullable
    private int product_id;

    @NotEmpty
    private String title;

    private int price;

    public ProductRepr(String title) {
        this.title = title;
    }

    public ProductRepr(int product_id, String title, int price) {
        this.product_id = product_id;
        this.title = title;
        this.price = price;
    }

    public ProductRepr(Long id, int product_id, String title, int price) {
        this.id = id;
        this.product_id = product_id;
        this.title = title;
        this.price = price;
    }

    public ProductRepr(Product product) {
        this.id = product.getId();
        this.product_id = product.getProduct_id();
        this.title = product.getTitle();
        this.price = product.getPrice();
    }

    public ProductRepr() {
    }
}
