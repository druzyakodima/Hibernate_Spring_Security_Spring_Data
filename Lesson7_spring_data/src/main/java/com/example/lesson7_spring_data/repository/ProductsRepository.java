package com.example.lesson7_spring_data.repository;

import com.example.lesson7_spring_data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductsRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    @Query("select p from Product p" +
            " where (p.price >= :priceMinFilter or :priceMinFilter is null) and" +
            "(p.price <= :priceMaxFilter or :priceMaxFilter is null)")
    List<Product> findWithFilter(@Param("priceMinFilter") Integer priceMinFilter,
                                 @Param("priceMaxFilter") Integer priceMaxFilter);

}
