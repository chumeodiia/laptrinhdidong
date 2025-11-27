package vn.iostar.shop_api.service;

import vn.iostar.shop_api.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getByCategory(Long categoryId);

    List<Product> getTop10BestSeller();

    List<Product> getTop10Newest();
}
