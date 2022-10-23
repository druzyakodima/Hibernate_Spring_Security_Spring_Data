package com.example.lesson7_spring_data.service;

import com.example.lesson7_spring_data.entity.ProductRepr;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.repository.CartRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@Server
@Service
public class CartServer {

    private CartRepository cartRepository;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void save(ProductRepr productRepr) {

        if (productRepr == null) {
            throw new NullPointerException();
        }
        cartRepository.save(productRepr);
    }


    public void delete(Long id) {
        cartRepository.delete(id);
    }

    public ProductRepr findById(Long id) {
        return cartRepository.findById(id);
    }

    public List<ProductRepr> showProduct() {
        return cartRepository.allProductInCart();
    }

    public CartServer() {
    }
}
