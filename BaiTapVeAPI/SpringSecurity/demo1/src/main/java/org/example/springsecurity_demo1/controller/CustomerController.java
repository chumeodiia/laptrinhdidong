package org.example.springsecurity_demo1.controller;


import org.example.springsecurity_demo1.model.Customer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final List<Customer> customers = List.of(
            Customer.builder().id(1).name("Nguyen Huu Trung").email("trung@gmail.con").build(),
            Customer.builder().id(2).name("Le Duc Viet").email("viet@gmail.con").build(),
            Customer.builder().id(3).name("Nguyen Van A").email("a@gmail.con").build()
    );
    // khong phan quyen ai cung goi duoc
    @GetMapping("/hello")
    public String hello() {
        return "Hello hi!";
    }

    // chi admin moi co the xem duoc tat ca cac customer
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Customer> getCustomerList() {
        return customers;
    }
    // Chỉ USER mới xem được 1 customer
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Customer getCustomerById(@PathVariable int id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
