package com.example.lesson7_spring_data.rest;

import com.example.lesson7_spring_data.service.cart_service.CartService;
import com.example.lesson7_spring_data.service.cart_service.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//swagger:
//http://localhost:8080/swagger-ui/index.html#/

@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/user/{userId}/product/{productId}")
    public void addToCart(@PathVariable("userId") Long userId,
                                 @PathVariable("productId") Long productId,
                                 @RequestParam("qty") Integer qty) {
        cartService.addProductForUser(productId,userId,qty);
    }

    @GetMapping("/user/{userId}")
    public List<LineItem> findItemForUser(@PathVariable("userId") Long userId) {
        return cartService.findAllItems(userId);
    }

    @PostMapping("/remove/user/{userId}/product/{productId}")
    public void removeProduct(@PathVariable("userId") Long userId,
                              @PathVariable("productId") Long productId,
                              @RequestParam("qty") Integer qty) {
        cartService.removeProductForUser(productId,userId,qty);
    }

    @DeleteMapping("/remove/user/{userId}")
    public void removeAllProduct(@PathVariable("userId") Long userId) {
        cartService.removeAllForUser(userId);
    }
}
