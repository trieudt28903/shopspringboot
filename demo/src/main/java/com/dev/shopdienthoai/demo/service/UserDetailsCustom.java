package com.dev.shopdienthoai.demo.service;


import com.dev.shopdienthoai.demo.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {
    private final UserService userService;

    public UserDetailsCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=this.userService.findUserByEmail(email);
        if(user==null)
        {
            throw new UsernameNotFoundException("username/password does not exist");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
