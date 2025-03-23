package com.example.recommendationengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.recommendationengine.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.categories LIKE %:category%")
    List<Product> findByCategory(@Param("category") String category);
    
    // Find top viewed products
    List<Product> findTop10ByOrderByViewCountDesc();
    
    // Find top rated products
    List<Product> findTop10ByOrderByAverageRatingDesc();
    
    // Find top purchased products
    List<Product> findTop10ByOrderByPurchaseCountDesc();
    
    // Find products within a price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

}
