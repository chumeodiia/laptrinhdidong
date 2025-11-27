package org.example.springsecurity_demo2.service;


import org.example.springsecurity_demo2.config.UserInfoUserDetails;
import org.example.springsecurity_demo2.entity.UserInfo;
import org.example.springsecurity_demo2.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserInfo userInfo = userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserInfoUserDetails(userInfo);
    }
}
