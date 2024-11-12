# EcoDeals Backend

Welcome to the **EcoDeals** backend repository! EcoDeals is a high-performance, scalable e-commerce platform built with **Spring Boot** to handle all backend operations for an advanced online marketplace. The platform powers core e-commerce functionalities like product catalog management, user authentication, shopping cart management, checkout, and much more.

## 🚀 Features

EcoDeals provides a wide range of features for both customers and administrators:

- **User Authentication and Authorization**: Secure login, registration, and JWT-based authentication.
- **Product Catalog**: Supports product categories, filtering, and search capabilities.
- **Shopping Cart and Checkout**: Persistent shopping cart for users, supporting multiple payment methods.
- **Order and Inventory Management**: Real-time inventory tracking and detailed order processing.
- **Admin Dashboard**: Tools for managing products, orders, users, and reporting.
- **Analytics and Reporting**: Sales and customer insights for better decision-making.
- **Personalization**: Product recommendations and customized promotions.

## 🛠️ Tech Stack

- **Spring Boot**: Backend framework for building RESTful APIs.
- **Spring Security**: For authentication and role-based authorization.
- **Spring Data JPA (Hibernate)**: For data persistence and seamless ORM integration.
- **MySQL/PostgreSQL**: Database for storing application data.
- **Redis**: Used for caching and session persistence.
- **Maven**: Dependency management and build automation.

## 🗂️ Project Structure

```plaintext
EcoDeals-backend/
├── src/
│   ├── main/
│   │   ├── java/com/ecodeals/
│   │   │   ├── controller/       # RESTful controllers
│   │   │   ├── model/            # Entity and data models
│   │   │   ├── repository/       # Data access layer
│   │   │   ├── service/          # Business logic layer
│   │   │   ├── config/           # Application and security configurations
│   │   └── resources/
│   │       ├── application.yml   # Application configuration file
│   │       └── static/           # Static resources (if any)
├── README.md
└── pom.xml                        # Maven dependencies
```

## 📦 Getting Started

### Prerequisites

Make sure you have the following installed:

- **Java 17+**
- **Maven 3.6+**
- **PostgreSQL**
- **Redis**

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/PoojithaIrosha/eco-deals-backend.git
   cd EcoDeals-backend
   ```

2. **Set up the database:**
    - Create a new PostgreSQL database called `eco_deals`.
    - Update your database credentials in `src/main/resources/application.yml`.

3. **Build and run the application:**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the application:**
    - The application runs by default at `http://localhost:8080`.

### Environment Variables

To configure environment-specific settings, set up the following environment variables:

- `DB_USERNAME`: Database username.
- `DB_PASSWORD`: Database password.
- `JWT_SECRET`: Secret key for JWT tokens.

## 📘 API Documentation

API documentation is provided using **Swagger** for testing and exploration. Once the application is running, you can access the Swagger documentation at:

```
http://localhost:8080/swagger-ui/
```

## 🤝 Contributing

Contributions are welcome! If you'd like to contribute, please fork the repository and create a pull request with a descriptive commit message. Make sure to run all tests and format your code before submission.

## 📜 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Thank you for checking out EcoDeals! We hope you enjoy using it as much as we enjoyed building it. 😊

---