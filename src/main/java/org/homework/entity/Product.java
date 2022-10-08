package org.homework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128, unique = true, nullable = false)
    private Long product_id;
    @Column(length = 128, unique = true, nullable = false)
    private String title;

    @Column(length = 128, nullable = false)
    private int price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Buyer> buyer;

    public Product(Long id, Long product_id, String title, int price) {
        this.id = id;
        this.product_id = product_id;
        this.title = title;
        this.price = price;
    }

    public Product(Long product_id, String title, int price, List<Buyer> buyer) {
        this.product_id = product_id;
        this.title = title;
        this.price = price;
        this.buyer = buyer;
    }

    public Product(Long product_id, String title, int price) {
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
