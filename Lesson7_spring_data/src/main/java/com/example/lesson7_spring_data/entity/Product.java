package com.example.lesson7_spring_data.entity;


import com.example.lesson7_spring_data.service.ProductRepr;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private int product_id;

    @Column(length = 128, unique = true, nullable = false)
    private String title;


    @Column(length = 128, nullable = false)
    private int price;

    public Product(ProductRepr productRepr) {
        this.id = productRepr.getId();
        this.product_id = productRepr.getProduct_id();
        this.title = productRepr.getTitle();
        this.price = productRepr.getPrice();
    }

    public Product(Long id, int product_id, String title, int price) {
        this.id = id;
        this.product_id = product_id;
        this.title = title;
        this.price = price;
    }

    public Product(int product_id, String title, int price) {
        this.product_id = product_id;
        this.title = title;
        this.price = price;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", title='" + title + '\'' +
                ", price=" + price + "}";
    }
}
