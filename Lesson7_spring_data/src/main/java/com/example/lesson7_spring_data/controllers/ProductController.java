package com.example.lesson7_spring_data.controllers;

import com.example.lesson7_spring_data.entity.product_entity.Product;
import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.cart_service.CartService;
import com.example.lesson7_spring_data.service.product_service.IProductService;
import com.example.lesson7_spring_data.entity.product_entity.ProductRepr;
import com.example.lesson7_spring_data.service.product_service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private IProductService productService;


    public ProductController() {
    }

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
                           @RequestParam("countElementOnPage") Optional<Integer> countElementOnPage,
                           @ModelAttribute("user")UserRepr user) {

        logger.info("List page requested");

        Page<ProductRepr> productsRepr = productService.findWithFilter
                (priceMinFilter.orElse(null),
                        priceMaxFilter.orElse(null),
                        currentPage.orElse(1) - 1,
                        countElementOnPage.orElse(10));

        model.addAttribute("productsRepr", productsRepr);

        return "product_templates/products";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/showFormAddProduct")
    public String create(Model model) {
        logger.info("Create new user request");

        model.addAttribute("product", new Product());
        return "product_templates/product-add-form";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/edit{id}")
    public String edit(@PathVariable("id") Long id, Model model){

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("product",productRepr);

        return "product_templates/product-add-form";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("product") ProductRepr product, BindingResult result) {
        logger.info("Update endpoint request");

        if (result.hasErrors()) {
            return "product_templates/product-add-form";
        }
        if (product.getProduct_id() != product.getProduct_id()) {
            result.rejectValue("password", "", "Password not matching");
            return "product_templates/product-add-form";
        }

        logger.info("Updating user with id {}", product.getId());
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/processForm")
    public String processForm(@ModelAttribute ProductRepr productRepr) {
        return "redirect:/product";
    }

    @GetMapping(path = "/showProductById")
    public String showProductById(Model model, @RequestParam("id") Long id) {

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("productRepr", productRepr);

        return "product_templates/product-form-result-find-id";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        logger.info("Product delete request");

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);

        productService.delete(productRepr.getId());

        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("product_templates/not_found_product");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
