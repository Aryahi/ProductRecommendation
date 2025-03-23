# Product Recommendation

## Overview
This project is a **Product Recommendation System** built using **Spring Boot** and **H2 Database**. It tracks user interactions such as views, purchases, and ratings to recommend popular products.

## Features Implemented
- **User Activity Tracking**: Captures product views, purchases, and ratings through API endpoints.
- **Popular Product Recommendation**: Returns a list of frequently viewed or purchased products.
- **Spring Boot REST API**: Provides seamless integration with e-commerce frontends.
- **H2 In-Memory Database**: Used for data storage and quick retrieval of recommendations.

## Endpoints
### **Tracking User Activity**
- `POST /track/view?userId={userId}&productId={productId}` → Track when a user views a product.
- `POST /track/purchase?userId={userId}&productId={productId}` → Track when a user purchases a product.
- `POST /track/rate?productId={productId}&rating={rating}` → Record user ratings for a product.

### **Getting Recommendations**
- `GET /recommend/popular` → Fetches the most viewed or purchased products.

## Tech Stack
- **Backend**: Spring Boot
- **Database**: H2 (In-memory)
- **API Documentation**: Postman

## To-Do
- Implement **collaborative filtering** to recommend products frequently viewed or purchased together.
- Optimize query performance using **caching (e.g., Redis)**.
- Store user activity data in **MongoDB** instead of H2 for scalability.
- Improve recommendation accuracy by integrating **machine learning models**.

## How to Run
1. Clone this repository:
https://github.com/Aryahi/ProductRecommendation.git
2. Navigate to the project directory:cd ProductRecommendation
3. Run the Spring Boot application:mvn spring-boot:run
4. Access the APIs using **Postman** or any API testing tool.

## Future Enhancements
- Personalization based on **user preferences and browsing history**.
- Integration with **React frontend** for a full-stack solution.
- Performance improvements for **real-time recommendations**.

---
