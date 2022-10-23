package com.example.lesson7_spring_data.repository;

import com.example.lesson7_spring_data.entity.ProductRepr;
import com.example.lesson7_spring_data.exception.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Setter
@Getter
@Component
public class CartRepository {

    List<ProductRepr> cartList;

    public CartRepository() {
        this.cartList = new ArrayList<>();
    }

    public void save(ProductRepr productRepr) {

        if (productRepr == null) {
            throw new NullPointerException();
        }
        cartList.add(productRepr);
    }

    public void delete(Long id) {
        ProductRepr productRepr = findById(id);
        cartList.remove(productRepr);
    }

    public List<ProductRepr> allProductInCart() {
        return cartList;
    }

    public ProductRepr findById(Long id) {

        return cartList.stream().filter(pr -> pr.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean contains(ProductRepr productRepr) {
        return cartList.contains(productRepr);
    }
}
