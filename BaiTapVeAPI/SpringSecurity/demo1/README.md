# Demo 1 – Spring Security cơ bản (In-memory Authentication)

## 1. Giới thiệu

Demo 1 minh hoạ cách tích hợp **Spring Security** đơn giản nhất:

- Sử dụng **in-memory user** (tài khoản khai báo thẳng trong code, không dùng DB).
- Sử dụng **form login mặc định** của Spring Security.
- Phân quyền theo **ROLE_USER / ROLE_ADMIN** cho các URL khác nhau.

Demo này giúp hiểu **khung làm việc của Spring Security** trước khi dùng DB, JWT hay Thymeleaf.

---

## 2. Công nghệ sử dụng

- Java 17
- Spring Boot 4.x
- Spring Web
- Spring Security

---

## 3. Cấu trúc chức năng chính

### 3.1. Cấu hình Security (SecurityConfig)

- Đăng ký các user mẫu trong bộ nhớ (in-memory):
  - `user/password` với role `ROLE_USER`
  - `admin/password` với role `ROLE_ADMIN`
- Cấu hình phân quyền URL, ví dụ:
  - `/` – cho phép tất cả truy cập (public)
  - `/user` – yêu cầu `ROLE_USER` hoặc `ROLE_ADMIN`
  - `/admin` – yêu cầu `ROLE_ADMIN`
- Bật form login mặc định của Spring:

```java
http
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/public").permitAll()
        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
        .requestMatchers("/admin").hasRole("ADMIN")
        .anyRequest().authenticated()
    )
    .formLogin(Customizer.withDefaults());
