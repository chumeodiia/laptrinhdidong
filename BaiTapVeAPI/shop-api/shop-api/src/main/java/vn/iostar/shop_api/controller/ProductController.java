package vn.iostar.shop_api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.iostar.shop_api.model.ApiResponse;
import vn.iostar.shop_api.service.IProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    // 2) Hiển thị tất cả sản phẩm theo từng danh mục
    // GET http://localhost:8088/v1/api/products/by-category/1
    @GetMapping("/by-category/{categoryId}")
    public ApiResponse getByCategory(@PathVariable Long categoryId) {
        return new ApiResponse(
                true,
                "Danh sách sản phẩm của danh mục " + categoryId,
                productService.getByCategory(categoryId)
        );
    }

    // 3) Hiển thị 10 sản phẩm có số lượng bán nhiều nhất
    // GET http://localhost:8088/v1/api/products/top10-bestseller
    @GetMapping("/top10-bestseller")
    public ApiResponse getTop10BestSeller() {
        return new ApiResponse(
                true,
                "Top 10 sản phẩm có số lượng bán nhiều nhất",
                productService.getTop10BestSeller()
        );
    }

    // 4) Hiển thị 10 sản phẩm được tạo <= 7 ngày
    // GET http://localhost:8088/v1/api/products/top10-newest
    @GetMapping("/top10-newest")
    public ApiResponse getTop10Newest() {
        return new ApiResponse(
                true,
                "Top 10 sản phẩm mới trong 7 ngày gần nhất",
                productService.getTop10Newest()
        );
    }
}
