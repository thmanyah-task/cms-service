package com.thmanyah.cms_service.service;


import com.thmanyah.cms_service.dto.JwtAuthResponse;
import com.thmanyah.cms_service.dto.SignIn;
import com.thmanyah.cms_service.dto.SignUp;

public interface UserService {

    Long signUp(SignUp signup);


    JwtAuthResponse signIn (SignIn signin);
}
