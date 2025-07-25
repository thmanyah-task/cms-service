package com.thmanyah.cms_service.security.controller;


import com.thmanyah.cms_service.security.dto.JwtAuthResponse;
import com.thmanyah.cms_service.security.dto.SignIn;
import com.thmanyah.cms_service.security.dto.SignUp;
import com.thmanyah.cms_service.security.service.UserService;
import com.thmanyah.cms_service.shared.dto.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ApiResponse<JwtAuthResponse> signIn (@RequestBody SignIn signin) {
        JwtAuthResponse jwtAuthResponse = userService.signIn(signin);
        return ApiResponse.<JwtAuthResponse>builder()
                .data(jwtAuthResponse)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();

    }

    @PostMapping("/sign-up")
    public ApiResponse<Long> signUp(@RequestBody SignUp signup) {
        Long userId = userService.signUp(signup);
        return ApiResponse.<Long>builder()
                .data(userId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }



}
