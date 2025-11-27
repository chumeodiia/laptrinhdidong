package org.example.springsecurity_demo3_thymeleaf.repository;



import org.example.springsecurity_demo3_thymeleaf.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

