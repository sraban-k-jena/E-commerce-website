# E-Commerce Website Backend

This project is a backend system for an e-commerce website, providing user and seller authentication, OTP-based verification, JWT-based security, and CRUD operations for users, sellers, products, and carts. The backend is built using Spring Boot and Spring Security, and it connects to a MySQL database for data storage.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Database Configuration](#database-configuration)
- [Environment Variables](#environment-variables)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Authentication**: OTP-based login/signup, JWT token generation, role-based access for Customer and Seller.
- **Seller Management**: Create, update, and delete seller profiles.
- **User Cart Management**: Add, remove, and update products in the user's cart.
- **Product Management**: CRUD operations for products.
- **OTP Verification**: OTP-based login and signup for both users and sellers.
- **JWT Security**: Secure endpoints using JWT tokens.
- **Spring Security**: Role-based access control.
  
## Technologies Used

- **Java** (JDK 11 or higher)
- **Spring Boot** - Backend framework
- **Spring Security** - For authentication and authorization
- **Spring Data JPA** - For ORM and database operations
- **MySQL** - Database
- **JWT (JSON Web Token)** - For secure token-based authentication
- **Maven** - For dependency management


# Database Configuration
- spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_website_db
- spring.datasource.username=<your-database-username>
- spring.datasource.password=<your-database-password>

- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true

# JWT Secret Key
- jwt.secret=<your-jwt-secret-key>

# Email SMTP Configuration
- spring.mail.host=<smtp-host>
- spring.mail.port=<smtp-port>
- spring.mail.username=<email-username>
- spring.mail.password=<email-password>
