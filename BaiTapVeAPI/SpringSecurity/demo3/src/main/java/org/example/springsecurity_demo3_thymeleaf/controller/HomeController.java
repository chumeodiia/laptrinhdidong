package org.example.springsecurity_demo3_thymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity_demo3_thymeleaf.entity.Product;
import org.example.springsecurity_demo3_thymeleaf.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;

    @GetMapping({"/", "/home"})
    public String index() {
        return "index";      // templates/index.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";      // templates/login.html
    }

    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> list = productRepository.findAll();
        model.addAttribute("products", list);
        return "products";   // templates/products.html
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";      // templates/admin.html
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";  // templates/access-denied.html
    }
}
