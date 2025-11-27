package org.example.springsecurity_demo2.config;

import org.example.springsecurity_demo2.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoUserDetails(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();
        // roles lưu 1 chuỗi, ví dụ "ROLE_ADMIN"
        this.authorities = List.of(new SimpleGrantedAuthority(userInfo.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Các hàm còn lại cho true hết cho đơn giản
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
