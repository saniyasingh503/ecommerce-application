# E-commerce Application - Microservices Architecture

## Overview
This project is a backend solution for an e-commerce web application developed using a microservices architecture. The system is designed to be scalable and maintainable, allowing for independent development, deployment, and enhancement of each microservice.

## Features
- **Admin Operations**:
  - Admin can add/remove new products to the inventory.
  - Admin can add/remove product details such as size, price, and design.
- **Product Service**:
  - Fetch product details (size, price, design) from relevant services.
- **User Operations**:
  - Users can view a list of all available products.
  - Add to cart and checkout functionality.
- **Notifications**:
  - Notifications are logged in the console based on events received from services.

## Solution Architecture
The microservice architecture provides a flexible and extendable backend for the e-commerce application. Each microservice is dedicated to a specific aspect of the application, which helps manage inter-service communication and facilitates future enhancements.

## Microservices

### 1. User Service
**Responsibilities**:
- Handles user registration, authentication, and profile management.
- Manages user information such as contact details and addresses.
- Provides JWT token for user authentication.

### 2. Product Service
**Responsibilities**:
- Manages product creation, updates, and deletion.
- Aggregates product data from the Price Service.

### 3. Price Service
**Responsibilities**:
- Manages and provides product pricing details.

### 4. Cart Service
**Responsibilities**:
- Manages the shopping cart for users.

### 5. Order Service
**Responsibilities**:
- Handles order creation, updates, and deletion.
- Manages the checkout process and order fulfillment.

### 6. Notification Service
**Responsibilities**:
- Handles the sending of notifications to users based on events.

## Architecture Components

### 1. API Gateway
**Responsibilities**:
- Routes incoming requests to the appropriate microservices.
- Handles cross-cutting concerns such as authentication and authorization.

### 2. Service Discovery
**Responsibilities**:
- Keeps track of all available service instances and their locations.
- Allows services to discover and communicate with each other dynamically.

### 3. Distributed Tracing
**Responsibilities**:
- Tracks requests as they flow through various microservices.
- Captures errors and exceptions with trace information.

## Flow for Adding a Product and Checkout Process

1. **Adding a Product**:
   - Admin registers using the User Service and generates a token.
   - Admin uses the Product Service to add a product, which calls the Price Service for pricing information.

2. **User Interaction**:
   - Users register and generate a token through the User Service.
   - Users view products via the Product Service.

3. **Add to Cart and Checkout**:
   - Users create a cart using the Cart Service.
   - Users add items to their cart.
   - During checkout, the Order Service processes the order and communicates with the Cart and Product Services.

4. **Handling Notifications**:
   - Notification Service listens to events and logs or sends notifications to users.

## Getting Started

### Prerequisites
- Java JDK (version 11 or higher)
- Docker
- Spring Boot
- Maven or Gradle (for dependency management)

### Running the Application
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/saniyasingh503/ecommerce-application.git
   cd ecommerce-application

### Accessing the Services
- **Eureka Server**: [http://localhost:8761/](http://localhost:8761/)
- **API Gateway**: [http://localhost:8083/](http://localhost:8083/)
- Access services through the API Gateway.

## Conclusion
This e-commerce application showcases a robust microservices architecture, allowing for scalability and ease of maintenance. The design is extendable, enabling future enhancements to the application.
