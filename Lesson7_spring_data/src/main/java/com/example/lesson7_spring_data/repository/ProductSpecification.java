package com.example.lesson7_spring_data.repository;

import com.example.lesson7_spring_data.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {

    public static Specification<Product> priceMin(Integer priceMin) {
        return (root, query, cb) -> cb.ge(root.get("price"), priceMin);
    }

    public static Specification<Product> priceMax(Integer priceMax) {
        return (root, query, cb) -> cb.le(root.get("price"), priceMax);
    }
}
