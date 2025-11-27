package vn.iostar.shop_api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, length = 200)
    private String name;

    // Một Category có nhiều Product
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore           // tránh vòng lặp vô hạn khi trả JSON
    private Set<Product> products;
}
