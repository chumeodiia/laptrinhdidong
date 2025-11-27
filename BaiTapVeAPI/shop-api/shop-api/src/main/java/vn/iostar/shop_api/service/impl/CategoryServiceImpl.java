package vn.iostar.shop_api.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.shop_api.entity.Category;
import vn.iostar.shop_api.repository.CategoryRepository;
import vn.iostar.shop_api.service.ICategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
