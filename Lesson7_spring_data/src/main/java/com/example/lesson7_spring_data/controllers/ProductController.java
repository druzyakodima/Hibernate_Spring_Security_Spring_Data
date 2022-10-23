package com.example.lesson7_spring_data.controllers;

import com.example.lesson7_spring_data.entity.Product;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.CartServer;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private IProductService productService;

    private CartServer cartServer;

    public ProductController() {
    }

    @Autowired
    public void setCartServer(CartServer cartServer) {
        this.cartServer = cartServer;
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
                           @RequestParam("countElementOnPage") Optional<Integer> countElementOnPage) {

        logger.info("List page requested");

        Page<ProductRepr> productsRepr = productService.findWithFilter
                (priceMinFilter.orElse(null),
                        priceMaxFilter.orElse(null),
                        currentPage.orElse(1) - 1,
                        countElementOnPage.orElse(10));

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

    @RequestMapping(path = "/showProductById")
    public String showProductById(Model model, @RequestParam("id") Long id) {

        productService.findById(id).orElseThrow(NotFoundException::new);

        return "product-form-result-find-id";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found_product");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
    @DeleteMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        logger.info("Product delete request");

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);

        productService.delete(productRepr.getId());

        return "redirect:/product";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("id") Long id) {
        logger.info("Add product in cart");

        ProductRepr productRepr = productService.findById(id).orElseThrow(NotFoundException::new);
        cartServer.save(productRepr);

        return "redirect:/product";
    }

    @GetMapping("/showCart")
    public String showCart(Model model){
        logger.info("Looked at the products in the cart");

        List<ProductRepr> listCart = cartServer.showProduct();
        model.addAttribute("cart", listCart);

        return "cart";
    }

    @DeleteMapping("/deleteProductInCart")
    public String deleteProductFromCart(@RequestParam Long id) {
        logger.info("Product delete request from shopping cart");

         cartServer.delete(id);

        return "redirect:/product/showCart";
    }
}
