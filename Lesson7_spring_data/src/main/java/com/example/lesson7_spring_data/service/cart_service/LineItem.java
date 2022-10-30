package com.example.lesson7_spring_data.service.cart_service;

import com.example.lesson7_spring_data.entity.product_entity.Product;
import com.example.lesson7_spring_data.entity.product_entity.ProductRepr;
import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;

@Data
public class LineItem {

    private ProductRepr product;

    private UserRepr user;

    private Integer qty;

    public LineItem(ProductRepr product, UserRepr user, Integer qty) {
        this.product = product;
        this.user = user;
        this.qty = qty;
    }


    public LineItem(long productId, long userId) {
        this.product = new ProductRepr();
        this.product.setId(productId);
        this.user = new UserRepr();
        this.user.setId(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return product.getId().equals(lineItem.product.getId()) && user.getId().equals(lineItem.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId(), user.getId());
    }
}
