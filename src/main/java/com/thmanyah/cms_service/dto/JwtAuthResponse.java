package com.thmanyah.cms_service.dto;


import lombok.*;

@Getter
@Setter
public class JwtAuthResponse {

    private String accessToken;

    private String tokenType = "Bearer";

    public JwtAuthResponse(){

    }
    public JwtAuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public JwtAuthResponse(String token) {

    }

    @Override
    public String toString() {
        return "JwtAuthResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }


}
