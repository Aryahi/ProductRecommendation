package com.example.recommendationengine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendationengine.model.Product;
import com.example.recommendationengine.service.RecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getRecommendationsForUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Product> recommendations = recommendationService.getPersonalizedRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/popular")
    public ResponseEntity<List<Product>> getPopularProducts(
            @RequestParam(defaultValue = "5") int limit) {
        List<Product> popularProducts = recommendationService.getPopularProducts(limit);
        return ResponseEntity.ok(popularProducts);  // 
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(defaultValue = "5") int limit) {
        List<Product> products = recommendationService.getProductsByPriceRange(minPrice, maxPrice, limit);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping("/track/view")
    public ResponseEntity<?> trackProductView(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        recommendationService.recordProductView(userId, productId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/track/purchase")
    public ResponseEntity<?> trackProductPurchase(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        recommendationService.recordProductPurchase(userId, productId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/track/rate")
    public ResponseEntity<?> rateProduct(
            @RequestParam Long productId,
            @RequestParam Double rating) {
        recommendationService.rateProduct(productId, rating);
        return ResponseEntity.ok().build();
    }
}
