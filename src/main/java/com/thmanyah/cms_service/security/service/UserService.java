package com.thmanyah.cms_service.security.service;


import com.thmanyah.cms_service.security.dto.JwtAuthResponse;
import com.thmanyah.cms_service.security.dto.SignIn;
import com.thmanyah.cms_service.security.dto.SignUp;

public interface UserService {

    Long signUp(SignUp signup);


    JwtAuthResponse signIn (SignIn signin);
}
