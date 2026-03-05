# AgroMart 🌾
## Agricultural E-Commerce Backend

AgroMart is a **Spring Boot–based RESTful backend application** designed for an agricultural e-commerce platform. The system allows users to manage farming products, categories, and address information while supporting image uploads and database persistence.

The project follows a **clean layered architecture** using Controller, Service, Repository, and DTO patterns to ensure scalability, maintainability, and separation of concerns.

---

## 🚀 Features

- Product management with full CRUD operations  
- Category management with image upload support  
- Address management APIs  
- Image upload for products and categories using MultipartFile  
- DTO-based request and response handling  
- Entity–DTO mapping using ModelMapper  
- Reusable BaseEntity with JPA lifecycle annotations  
- Input validation using Jakarta Validation  
- JPA entity relationships with Many-to-One mapping  
- RESTful API design following Spring Boot best practices  

---

## 🏗 Architecture

The project follows a layered architecture:

```
Controller → Service → Repository → Database
```

### Controller Layer
Handles HTTP requests and responses.

### Service Layer
Contains business logic.

### Repository Layer
Handles database operations using Spring Data JPA.

### DTO Layer
Transfers data between client and server without exposing entities.

---

## 🛠 Tech Stack

### Backend
- Java  
- Spring Boot  
- Spring Data JPA  
- Hibernate  

### Database
- MySQL  

### Tools & Libraries
- ModelMapper  
- Maven  
- Multipart File Upload  
- Jakarta Validation  

---

## 📂 Project Structure

```
src
 ├── controller
 ├── service
 ├── serviceimpl
 ├── repository
 ├── model
 ├── dto
 ├── config
 └── uploads
```

---

## 🔗 API Modules

The application provides REST APIs for:

- Product Management  
- Category Management  
- Address Management  

### Example Endpoints

```
POST   /api/products
GET    /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
```

---

## 📸 File Upload Support

The application supports uploading images for:

- Product images  
- Additional product images  
- Category images  

Files are stored dynamically in the project upload directory.

---

## 🎯 Future Enhancements

Planned improvements for the project:

- User authentication using Spring Security and JWT  
- Shopping cart functionality  
- Order management system  
- Payment gateway integration  
- React-based frontend application  

---

## 👨‍💻 Author

**Sathvik PM**  
Java Full Stack Developer
