package vn.iotstar.Controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IStorageService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/category")
@CrossOrigin
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
    }

    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addCategory(
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(false, "Category đã tồn tại", optCategory.get()));
        } else {
            Category category = new Category();
            // lưu file nếu có
            if (icon != null && !icon.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                String storeFilename = storageService.getSorageFilename(icon, uuString);
                category.setIcon(storeFilename);
                storageService.store(icon, storeFilename);
            }
            category.setCategoryName(categoryName);
            categoryService.save(category);
            return new ResponseEntity<>(new Response(true, "Thêm Thành công", category), HttpStatus.OK);
        }
    }

    @PutMapping(path = "/updateCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCategory(
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        } else {
            Category c = optCategory.get();
            if (icon != null && !icon.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                String storeFilename = storageService.getSorageFilename(icon, uuString);
                c.setIcon(storeFilename);
                storageService.store(icon, storeFilename);
            }
            c.setCategoryName(categoryName);
            categoryService.save(c);
            return new ResponseEntity<>(new Response(true, "Cập nhật Thành công", c), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        } else {
            Category c = optCategory.get();
            // xóa file icon nếu muốn (bắt try/catch)
            try {
                if (c.getIcon() != null) {
                    storageService.delete(c.getIcon());
                }
            } catch (Exception ex) {
                // log but continue
                System.err.println("Xóa file thất bại: " + ex.getMessage());
            }
            categoryService.delete(c);
            return new ResponseEntity<>(new Response(true, "Xóa Thành công", c), HttpStatus.OK);
        }
    }
}
