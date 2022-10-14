package com.example.lesson7_spring_data.service;

import com.example.lesson7_spring_data.entity.ProductRepr;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    Optional<ProductRepr> findById(Long id);

    List<ProductRepr> findAll();

    Page<ProductRepr> findWithFilter(Integer priceMinFilter,
                                     Integer priceMaxFilter,
                                     Integer currentPage,
                                     Integer countPage);

    void delete(Long id);

    void save(ProductRepr productRepr);

}
