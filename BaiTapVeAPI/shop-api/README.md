
# 🛒 Shop API – Spring Boot 3 (Mobile Programming Assignment)

Dự án backend được xây dựng bằng **Spring Boot 3** phục vụ môn **Lập trình di động**, dùng để cung cấp API REST cho ứng dụng bán hàng (sản phẩm – danh mục).

## 🌟 Chức năng đã cài đặt

### 1. Quản lý danh mục (Category)

- Hiển thị **tất cả danh mục** của hệ thống.
- Mỗi danh mục có thể chứa nhiều sản phẩm.

**API:**

- `GET /api/categories`  
  → Trả về danh sách toàn bộ danh mục.
  <img width="485" height="982" alt="image" src="https://github.com/user-attachments/assets/6e3504c0-ef2f-4a53-9788-ed4a5aafda5d" />


---

### 2. Quản lý sản phẩm (Product)

Hệ thống sản phẩm được tổ chức theo danh mục, và hỗ trợ các API sau:

1. **Hiển thị tất cả sản phẩm theo từng danh mục**

   - **API:** `GET /api/products/by-category/{categoryId}`
   - **Mô tả:** Trả về danh sách tất cả sản phẩm thuộc một danh mục cụ thể.
   - **Ví dụ:**  
     `GET http://localhost:8088/api/products/by-category/1`
     <img width="757" height="991" alt="image" src="https://github.com/user-attachments/assets/6cf890b2-18fa-44a3-ade2-6f716430e3a2" />

     

2. **Hiển thị 10 sản phẩm có số lượng bán nhiều nhất**

   - **API:** `GET /api/products/top10-bestseller`
   - **Mô tả:** Lấy top 10 sản phẩm có trường `quantity` lớn nhất (coi như số lượng bán nhiều nhất).
     <img width="724" height="984" alt="Screenshot 2025-11-25 164639" src="https://github.com/user-attachments/assets/da7c727f-4fb3-4e7c-b53f-47354b1fb378" />


3. **Hiển thị 10 sản phẩm được tạo trong vòng ≤ 7 ngày**

   - **API:** `GET /api/products/top10-newest`
   - **Mô tả:** Lấy 10 sản phẩm mới nhất có `createDate` nằm trong **7 ngày gần nhất** tính từ thời điểm hiện tại.
    <img width="757" height="991" alt="Screenshot 2025-11-25 164704" src="https://github.com/user-attachments/assets/90a6cd4d-90d4-46be-90f7-6f37e667b787" />


---

### 3. Định dạng dữ liệu trả về

Tất cả API sử dụng chung một model phản hồi:

```json
{
  "status": true,
  "message": "Mô tả ngắn gọn kết quả",
  "data": { ... hoặc [...] }
}
