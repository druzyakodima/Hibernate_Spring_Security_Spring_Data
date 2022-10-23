package com.example.lesson7_spring_data.rest;

import com.example.lesson7_spring_data.entity.ProductRepr;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.CartServer;
import com.example.lesson7_spring_data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//swagger:
//http://localhost:8080/swagger-ui/index.html#/

@RestController
@RequestMapping("/api/v2/cart")
public class CartResource {

    private final CartServer cartServer;
    private final ProductService productService;

    @Autowired
    public CartResource(CartServer cartServer, ProductService productService) {
        this.cartServer = cartServer;
        this.productService = productService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public List<ProductRepr> showCart(){
        List<ProductRepr> listCart = cartServer.showProduct();
        return listCart;
    }

    @DeleteMapping("/delete{id}")
    public void deleteProductFromCart(@PathVariable Long id) {

        cartServer.delete(id);

    }

    @PostMapping("/addToCart")
    public ProductRepr addToCart(@RequestParam("id") Long id) {

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);
        cartServer.save(productRepr);

        return productRepr;
    }
}
