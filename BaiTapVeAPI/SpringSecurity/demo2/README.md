
---

### 📄 README cho **Demo 2** – REST API + JWT (Stateless)

```markdown
# Demo 2 – Spring Security với REST API + JWT

## 1. Giới thiệu

Demo 2 minh hoạ cách bảo vệ **REST API** bằng **JWT token**, không dùng session, không dùng view (Thymeleaf).

- User gửi `username/password` để **login**, server trả về **JWT**.
- Các request tiếp theo bắt buộc gửi header:  
  `Authorization: Bearer <token>`.
- Không dùng form login, không dùng giao diện, chỉ API (Postman / curl / HTTP client).

---

## 2. Công nghệ sử dụng

- Java 17
- Spring Boot 4.x
- Spring Web
- Spring Security (stateless)
- JPA (có thể dùng hoặc in-memory user)
- JWT (jjwt / java-jwt tuỳ project bạn dùng)

---

## 3. Các endpoint chính

Ví dụ:

- `POST /api/auth/login`  
  – Nhận JSON:
  ```json
  {
    "username": "user@example.com",
    "password": "123456"
  }
