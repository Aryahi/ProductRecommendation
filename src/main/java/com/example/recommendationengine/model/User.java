package com.example.recommendationengine.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users") // 'users' is a reserved keyword in PostgreSQL
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    @Column(unique = true)
    private String email;
    
    // Store user preferences (e.g., preferred categories) as a comma-separated string
    private String preferences;
    
    // Store viewed product IDs
    @ElementCollection
    @CollectionTable(name = "user_viewed_products", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "product_id")
    private List<Long> viewedProducts = new ArrayList<>();
    
    // Store purchased product IDs
    @ElementCollection
    @CollectionTable(name = "user_purchased_products", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "product_id")
    private List<Long> purchasedProducts = new ArrayList<>();
}