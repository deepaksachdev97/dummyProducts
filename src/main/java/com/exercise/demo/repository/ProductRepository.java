package com.exercise.demo.repository;

import com.exercise.demo.entity.Product;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Modifying
    @Transactional
    @Query("update Product p set p.price=:price where p.id=:id")
    int updateProductPriceById(@Param("id") Integer id, @Param("price") BigDecimal price);
}
