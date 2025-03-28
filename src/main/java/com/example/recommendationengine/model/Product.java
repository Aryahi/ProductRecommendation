package com.example.recommendationengine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private Double price;
    
    // Categories represented as a comma-separated string
    private String categories;
    
    // Attributes for collaborative filtering
    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "purchase_count")
    private Integer purchaseCount = 0;

    @Column(name = "average_rating")
    private Double averageRating = 0.0;

}
