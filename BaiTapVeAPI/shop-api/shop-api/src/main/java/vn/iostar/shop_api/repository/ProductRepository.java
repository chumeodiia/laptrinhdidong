package vn.iostar.shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.shop_api.entity.Product;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 2) Lấy tất cả sản phẩm theo danh mục
    List<Product> findByCategory_CategoryId(Long categoryId);

    // 3) Lấy 10 sản phẩm có quantity lớn nhất (bán nhiều nhất)
    List<Product> findTop10ByOrderByQuantityDesc();

    // 4) Lấy 10 sản phẩm mới tạo trong 7 ngày gần nhất
    List<Product> findTop10ByCreateDateAfterOrderByCreateDateDesc(Date date);
}
