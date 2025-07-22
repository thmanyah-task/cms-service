package com.thmanyah.cms_service.controller;


import com.thmanyah.cms_service.dto.ApiResponse;
import com.thmanyah.cms_service.dto.JwtAuthResponse;
import com.thmanyah.cms_service.dto.SignIn;
import com.thmanyah.cms_service.dto.SignUp;
import com.thmanyah.cms_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
