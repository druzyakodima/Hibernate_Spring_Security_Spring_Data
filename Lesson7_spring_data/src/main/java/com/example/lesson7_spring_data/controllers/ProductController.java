package com.example.lesson7_spring_data.controllers;

import com.example.lesson7_spring_data.entity.Product;
import com.example.lesson7_spring_data.service.IProductService;
import com.example.lesson7_spring_data.service.ProductRepr;
import com.example.lesson7_spring_data.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private IProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model, ProductRepr productRepr) {
        logger.info("List page requested");

        model.addAttribute(productRepr);
        model.addAttribute("products", productService.findAll());
        return "products";
    }


    @GetMapping("/showFormAddProduct")
    public String create(Model model) {
        logger.info("Create new user request");

        model.addAttribute("product", new Product());
        return "product-add-form";
    }

    @RequestMapping("/add")
    public String showSimpleForm(@ModelAttribute("product") ProductRepr productRepr) {
        logger.info("Add new product");
        if (productRepr != null) {
            productService.save(productRepr);
            return "redirect:/product";
        }
        return "product-null";
    }

    @RequestMapping("/selectFilter")
    public String selectFilter() {
        return "";
    }
    @RequestMapping("/processForm")
    public String processForm(@ModelAttribute("product") ProductRepr productRepr) {
        return "redirect:/product";
    }

    @DeleteMapping("/remove")
    public String remove(@RequestParam("id") Long id){
        logger.info("Product delete request");

        productService.delete(id);
        return "redirect:/product";
    }

    @RequestMapping(path = "/showProductById")
    public String showProductById(Model model, @RequestParam("id") Long id) {

        model.addAttribute("productRepr", productService.findById(id).get());
        return "product-form-result-find-id";
    }
}
