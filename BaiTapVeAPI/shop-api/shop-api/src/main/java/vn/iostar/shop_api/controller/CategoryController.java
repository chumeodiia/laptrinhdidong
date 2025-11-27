package vn.iostar.shop_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.iostar.shop_api.model.ApiResponse;
import vn.iostar.shop_api.service.ICategoryService;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    // 1) Hiển thị tất cả danh mục của đề tài
    // GET http://localhost:8088/v1/api/categories
    @GetMapping
    public ApiResponse getAllCategories() {
        return new ApiResponse(
                true,
                "Danh sách tất cả danh mục",
                categoryService.getAllCategories()
        );
    }
}
