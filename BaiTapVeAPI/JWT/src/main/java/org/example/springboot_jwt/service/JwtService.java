package org.example.springboot_jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpirationMs;

    // Lấy username (email) từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Hàm generic đọc 1 claim bất kỳ
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // Tạo token không có extra claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    // Tạo token kèm extra claims
    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignKey()) // ký HMAC-SHA bằng secret
                .compact();
    }

    // Kiểm tra token có hợp lệ với user không
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // token hết hạn?
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())    // kiểm tra chữ ký
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Tạo SecretKey từ chuỗi hex trong application.properties
    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
