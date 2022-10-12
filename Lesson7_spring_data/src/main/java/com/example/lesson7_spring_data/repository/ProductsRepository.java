package com.example.lesson7_spring_data.repository;

import com.example.lesson7_spring_data.entity.Product;
import com.example.lesson7_spring_data.service.ProductRepr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductsRepository extends JpaRepository<Product,Long> {

}
