package com.example.lesson7_spring_data.controllers;

import com.example.lesson7_spring_data.entity.Product;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.IProductService;
import com.example.lesson7_spring_data.entity.ProductRepr;
import com.example.lesson7_spring_data.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    public ProductController() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private IProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model,
                           @ModelAttribute("productRepr") ProductRepr productRepr,
                           @RequestParam("priceMinFilter") Optional<Integer> priceMinFilter,
                           @RequestParam("priceMaxFilter") Optional<Integer> priceMaxFilter,
                           @RequestParam("currentPage") Optional<Integer> currentPage,
                           @RequestParam("countPage") Optional<Integer> countPage) {

        logger.info("List page requested");

        Page<ProductRepr> productsRepr = productService.findWithFilter
                (priceMinFilter.orElse(null),
                        priceMaxFilter.orElse(null),
                        currentPage.orElse(1) - 1,
                        countPage.orElse(10));

        model.addAttribute("productsRepr", productsRepr);

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
        return "not_found_product";
    }

    @RequestMapping("/processForm")
    public String processForm(@ModelAttribute("product") ProductRepr productRepr) {
        return "redirect:/product";
    }

    @DeleteMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        logger.info("Product delete request");

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);

        productService.delete(productRepr.getId());

        return "redirect:/product";
    }

    @RequestMapping(path = "/showProductById")
    public String showProductById(Model model, @RequestParam("id") Long id) {

        model.addAttribute("productRepr", productService.findById(id).orElseThrow(NotFoundException::new));
        return "product-form-result-find-id";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found_product");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
