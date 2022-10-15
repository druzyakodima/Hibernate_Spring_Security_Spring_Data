package com.example.lesson7_spring_data.rest;

import com.example.lesson7_spring_data.entity.ProductRepr;
import com.example.lesson7_spring_data.exception.BadRequestException;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public List<ProductRepr> findAll() {
        return productService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ProductRepr findById(@PathVariable("id") Long id) {
        return productService.findById(id).orElseThrow(NotFoundException :: new);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ProductRepr create(@RequestBody ProductRepr productRepr) {
        if (productRepr.getId() != null) {
            throw new BadRequestException();
        }
        productService.save(productRepr);
        return productRepr;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> BadRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request", HttpStatus.NOT_FOUND);
    }
}
