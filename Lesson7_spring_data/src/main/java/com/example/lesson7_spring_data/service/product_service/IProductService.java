package com.example.lesson7_spring_data.service.product_service;

import com.example.lesson7_spring_data.entity.product_entity.ProductRepr;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    Optional<ProductRepr> findById(Long id);

    List<ProductRepr> findAll();

    Page<ProductRepr> findWithFilter(Integer priceMinFilter,
                                     Integer priceMaxFilter,
                                     Integer currentPage,
                                     Integer countElementOnPage);

    void delete(Long id);

    void save(ProductRepr productRepr);

}
