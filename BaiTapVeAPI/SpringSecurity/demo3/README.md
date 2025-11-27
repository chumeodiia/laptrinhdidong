
---

### 📄 README cho **Demo 3** – Spring Security + Thymeleaf + MySQL (Login/Register + Role)

```markdown
# Demo 3 – Spring Security + Thymeleaf + MySQL (Login / Register / Role)

## 1. Giới thiệu

Demo 3 là phiên bản "gần với thực tế" nhất trong 3 demo:

- Dùng **Spring Security** + **Thymeleaf** + **JPA + MySQL**.
- Có **trang đăng nhập (login)**, **đăng ký (register)**.
- Lưu user thật vào DB với **mật khẩu được mã hoá**.
- Phân quyền:
  - `ROLE_USER`
  - `ROLE_ADMIN`
- Dùng template engine Thymeleaf để render HTML.

---

## 2. Công nghệ sử dụng

- Java 17
- Spring Boot 4.x
- Spring Web (MVC)
- Spring Security
- Spring Data JPA
- MySQL
- Thymeleaf
- Lombok

---

## 3. Cấu hình database

Trong `application.properties`:

```properties
spring.application.name=springsecurity_demo3_thymeleaf
server.port=8090

spring.datasource.url=jdbc:mysql://localhost:3306/SpringBootLoginRole?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh
spring.datasource.username=root
spring.datasource.password=11111

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
