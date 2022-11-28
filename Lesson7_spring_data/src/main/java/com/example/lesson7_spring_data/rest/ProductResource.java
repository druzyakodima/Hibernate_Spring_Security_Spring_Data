package com.example.lesson7_spring_data.rest;

import com.example.lesson7_spring_data.entity.ProductRepr;
import com.example.lesson7_spring_data.exception.BadRequestException;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//swagger:
//http://localhost:8080/swagger-ui/index.html#/

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService productService;


    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/filter")
    public Page<ProductRepr> listPage(Model model,
                                      @ModelAttribute("productRepr") ProductRepr productRepr,
                                      @RequestParam("priceMinFilter") Optional<Integer> priceMinFilter,
                                      @RequestParam("priceMaxFilter") Optional<Integer> priceMaxFilter,
                                      @Parameter(example = "1") @RequestParam("currentPage") Optional<Integer> currentPage,
                                      @RequestParam("countElementOnPage") Optional<Integer> countElementOnPage) {

        Page<ProductRepr> productsRepr = productService.findWithFilter
                (priceMinFilter.orElse(null),
                        priceMaxFilter.orElse(null),
                        currentPage.orElse(1) - 1,
                        countElementOnPage.orElse(10));

        model.addAttribute("productsRepr", productsRepr);

        return productsRepr;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public List<ProductRepr> findAll() {
        return productService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ProductRepr findById(@PathVariable("id") Long id) {
        return productService.findById(id).orElseThrow(NotFoundException::new);
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
