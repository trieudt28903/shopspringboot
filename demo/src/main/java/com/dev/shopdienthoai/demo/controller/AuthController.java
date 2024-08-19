package com.dev.shopdienthoai.demo.controller;

import com.dev.shopdienthoai.demo.domain.User;
import com.dev.shopdienthoai.demo.domain.dto.LoginDTO;
import com.dev.shopdienthoai.demo.domain.dto.ResLoginDTO;
import com.dev.shopdienthoai.demo.service.UserService;
import com.dev.shopdienthoai.demo.until.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        // nạp input gồm username/password vào security
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //create a token
        String access_token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDTO res = new ResLoginDTO();
        User getUserLogin = this.userService.findUserByEmail(loginDTO.getUsername());

        if(getUserLogin!=null) {
            ResLoginDTO.UserLogin userLogin=new ResLoginDTO.UserLogin(getUserLogin.getId()
                    ,getUserLogin.getEmail(),getUserLogin.getName());
            res.setUser(userLogin);
        }
        res.setAccessToken(access_token);
        return ResponseEntity.ok().body(res);
    }
}
