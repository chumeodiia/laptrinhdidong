package vn.iostar.shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.shop_api.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
