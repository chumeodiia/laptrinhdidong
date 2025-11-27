package vn.iostar.shop_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 500)
    private String name;

    // Số lượng bán / tồn kho (tuỳ bạn định nghĩa)
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    private String image;

    @Column(columnDefinition = "text")
    private String description;

    private double discount;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private short status;

    // Nhiều sản phẩm thuộc 1 danh mục
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
