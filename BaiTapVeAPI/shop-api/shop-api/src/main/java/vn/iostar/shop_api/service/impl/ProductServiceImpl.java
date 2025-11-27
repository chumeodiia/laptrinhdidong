package vn.iostar.shop_api.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.shop_api.entity.Product;
import vn.iostar.shop_api.repository.ProductRepository;
import vn.iostar.shop_api.service.IProductService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getByCategory(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Product> getTop10BestSeller() {
        return productRepository.findTop10ByOrderByQuantityDesc();
    }

    @Override
    public List<Product> getTop10Newest() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);  // lấy ngày cách đây 7 ngày
        Date sevenDaysAgo = cal.getTime();
        return productRepository.findTop10ByCreateDateAfterOrderByCreateDateDesc(sevenDaysAgo);
    }
}
