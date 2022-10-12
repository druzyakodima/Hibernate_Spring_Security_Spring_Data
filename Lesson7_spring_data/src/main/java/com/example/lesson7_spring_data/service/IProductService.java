package com.example.lesson7_spring_data.service;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Optional<ProductRepr> findById(Long id);

    List<ProductRepr> findAll();

    void delete(Long id);

    void save(ProductRepr productRepr);

}
