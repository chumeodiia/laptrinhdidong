package vn.iostar.shop_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private boolean status;  // true/false
    private String message;  // mô tả
    private Object data;     // dữ liệu (List<Category>, List<Product>...)
}
