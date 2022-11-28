package com.example.lesson7_spring_data.controllers;

import com.example.lesson7_spring_data.entity.product_entity.ProductRepr;
import com.example.lesson7_spring_data.entity.user_entity.User;
import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.security.UserAuthService;
import com.example.lesson7_spring_data.service.cart_service.CartService;
import com.example.lesson7_spring_data.service.cart_service.LineItem;
import com.example.lesson7_spring_data.service.product_service.ProductService;
import com.example.lesson7_spring_data.service.user_service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController {

    private ProductService productService;

    private UserAuthService userAuthService;
    private CartService cartService;

    @Autowired
    public CartController(ProductService productService, CartService cartServer, UserAuthService userAuthService) {
        this.productService = productService;
        this.cartService = cartServer;
        this.userAuthService = userAuthService;
    }

    @GetMapping()
    public String showCart(Model model) {
        log.info("Looked at the products in the cart");
        User user = userAuthService.getCurrentUser();

        List<LineItem> listCart = cartService.findAllItems(user.getId());
        model.addAttribute("cart", listCart);

        return "cart_templates/cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId) {
        log.info("Add product in cart");

        User user = userAuthService.getCurrentUser();
        ProductRepr productRepr = productService.findById(productId).orElseThrow(NotFoundException::new);
        long qty = 0;

        if (cartService.getLineItemForUser() != null) {
            qty = cartService.findAllItems(user.getId())
                    .stream()
                    .filter(product -> product.getProduct().equals(productRepr))
                    .count();
        }

        cartService.addProductForUser(productRepr.getId(), user.getId(), Math.toIntExact(++qty));

        return "redirect:/product";
    }

    @PostMapping("/deleteProductInCart")
    public String deleteProductFromCart(@RequestParam("productId") Long productId) {
        log.info("Product delete request from shopping cart");
        ProductRepr productRepr = productService.findById(productId).orElseThrow(NotFoundException::new);
        User user = userAuthService.getCurrentUser();

        long qty = cartService.findAllItems(user.getId())
                .stream()
                .filter(product -> product.getProduct().equals(productRepr))
                .count();

        cartService.removeProductForUser(productRepr.getId(), user.getId(), Math.toIntExact(qty));

        return "redirect:/cart";
    }

    @DeleteMapping("/clear")
    public String clearCart() {

        User user = userAuthService.getCurrentUser();
        cartService.removeAllForUser(user.getId());
        return "redirect:/cart";
    }
}
