package com.example.recommendationengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recommendationengine.model.Product;
import com.example.recommendationengine.model.User;
import com.example.recommendationengine.repository.ProductRepository;
import com.example.recommendationengine.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Get personalized recommendations for a user
     */
    public List<Product> getPersonalizedRecommendations(Long userId, int limit) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isEmpty()) {
            // If user doesn't exist, return popular products
            return getPopularProducts(limit);
        }
        
        User user = userOptional.get();
        
        // 1. Content-based filtering (based on user preferences)
        List<Product> contentBasedRecommendations = getContentBasedRecommendations(user, limit);
        
        // 2. Collaborative filtering (based on user behavior)
        List<Product> collaborativeRecommendations = getCollaborativeRecommendations(user, limit);
        
        // 3. Combine recommendations (simple approach: alternate between the two lists)
        List<Product> combinedRecommendations = new ArrayList<>();
        Iterator<Product> contentIter = contentBasedRecommendations.iterator();
        Iterator<Product> collabIter = collaborativeRecommendations.iterator();
        
        while (combinedRecommendations.size() < limit && (contentIter.hasNext() || collabIter.hasNext())) {
            if (contentIter.hasNext()) {
                Product product = contentIter.next();
                if (!combinedRecommendations.contains(product)) {
                    combinedRecommendations.add(product);
                }
            }
            
            if (collabIter.hasNext() && combinedRecommendations.size() < limit) {
                Product product = collabIter.next();
                if (!combinedRecommendations.contains(product)) {
                    combinedRecommendations.add(product);
                }
            }
        }
        
        // If we still don't have enough recommendations, add popular products
        if (combinedRecommendations.size() < limit) {
            List<Product> popularProducts = getPopularProducts(limit);
            for (Product product : popularProducts) {
                if (!combinedRecommendations.contains(product) && combinedRecommendations.size() < limit) {
                    combinedRecommendations.add(product);
                }
            }
        }
        
        return combinedRecommendations;
    }
    
    /**
     * Content-based filtering: recommend products similar to user's preferences
     */
    private List<Product> getContentBasedRecommendations(User user, int limit) {
        // Extract user's preferred categories
        List<String> userPreferences = new ArrayList<>();
        if (user.getPreferences() != null && !user.getPreferences().isEmpty()) {
            userPreferences = Arrays.asList(user.getPreferences().split(","));
        }
        
        // Get products from preferred categories
        Set<Product> recommendedProducts = new HashSet<>();
        
        // If user has preferences, use them
        if (!userPreferences.isEmpty()) {
            for (String category : userPreferences) {
                recommendedProducts.addAll(productRepository.findByCategory(category.trim()));
            }
        }
        
        // If we have viewed products, analyze their categories too
        if (user.getViewedProducts() != null && !user.getViewedProducts().isEmpty()) {
            for (Long productId : user.getViewedProducts()) {
                Optional<Product> productOpt = productRepository.findById(productId);
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    if (product.getCategories() != null && !product.getCategories().isEmpty()) {
                        String[] categories = product.getCategories().split(",");
                        for (String category : categories) {
                            recommendedProducts.addAll(productRepository.findByCategory(category.trim()));
                        }
                    }
                }
            }
        }
        
        // Remove products the user has already purchased
        if (user.getPurchasedProducts() != null) {
            recommendedProducts.removeIf(product -> user.getPurchasedProducts().contains(product.getId()));
        }
        
        // Convert to list and limit results
        return recommendedProducts.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Collaborative filtering: recommend products based on user behavior
     */
    private List<Product> getCollaborativeRecommendations(User user, int limit) {
        // Simple implementation: get top-rated and top-purchased products
        List<Product> topProducts = new ArrayList<>();
        
        // Add top-rated products
        topProducts.addAll(productRepository.findTop10ByOrderByAverageRatingDesc());
        
        // Add top-purchased products
        topProducts.addAll(productRepository.findTop10ByOrderByPurchaseCountDesc());
        
        // Remove duplicates and products the user has already purchased
        Set<Product> uniqueProducts = new HashSet<>(topProducts);
        if (user.getPurchasedProducts() != null) {
            uniqueProducts.removeIf(product -> user.getPurchasedProducts().contains(product.getId()));
        }
        
        // Convert back to list and limit results
        return uniqueProducts.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Get popular products (for new users or fallback)
     */
    public List<Product> getPopularProducts(int limit) {
        // Combine top viewed, top rated, and top purchased products
        Set<Product> popularProducts = new HashSet<>();
        popularProducts.addAll(productRepository.findTop10ByOrderByViewCountDesc());
        popularProducts.addAll(productRepository.findTop10ByOrderByAverageRatingDesc());
        popularProducts.addAll(productRepository.findTop10ByOrderByPurchaseCountDesc());
        
        // Convert to list and limit results
        return popularProducts.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Record that a user viewed a product (for behavior tracking)
     */
    @Transactional
    public void recordProductView(Long userId, Long productId) {
        // Update product view count
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setViewCount(product.getViewCount() + 1);
            productRepository.save(product);
        }
        
        // Update user's viewed products
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Long> viewedProducts = user.getViewedProducts();
            if (viewedProducts == null) {
                viewedProducts = new ArrayList<>();
            }
            if (!viewedProducts.contains(productId)) {
                viewedProducts.add(productId);
                user.setViewedProducts(viewedProducts);
                userRepository.save(user);
            }
        }
    }
    
    /**
     * Record that a user purchased a product (for behavior tracking)
     */
    @Transactional
    public void recordProductPurchase(Long userId, Long productId) {
        // Update product purchase count
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setPurchaseCount(product.getPurchaseCount() + 1);
            productRepository.save(product);
        }
        
        // Update user's purchased products
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Long> purchasedProducts = user.getPurchasedProducts();
            if (purchasedProducts == null) {
                purchasedProducts = new ArrayList<>();
            }
            if (!purchasedProducts.contains(productId)) {
                purchasedProducts.add(productId);
                user.setPurchasedProducts(purchasedProducts);
                userRepository.save(user);
            }
        }
    }
    
    /**
     * Rate a product (for behavior tracking)
     */
    @Transactional
    public void rateProduct(Long productId, Double rating) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            
            // Simple approach: Update the average rating directly
            // In a real system, you would store individual ratings and calculate the average
            double currentAvg = product.getAverageRating();
            int viewCount = product.getViewCount();
            
            // Calculate new average (simple approach assuming equal weight to all ratings)
            double newAvg = (currentAvg * viewCount + rating) / (viewCount + 1);
            product.setAverageRating(newAvg);
            
            productRepository.save(product);
        }
    }
    
    /**
     * Get products within a specific price range
     */
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice, int limit) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream().limit(limit).collect(Collectors.toList());
    }
}