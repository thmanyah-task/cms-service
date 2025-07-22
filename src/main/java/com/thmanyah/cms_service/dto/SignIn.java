package com.thmanyah.cms_service.dto;


import lombok.*;

@Getter
@Setter
public class SignIn {

    private String userName;
    private String password;


    @Override
    public String toString() {
        return "SignIn{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public SignIn(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public SignIn(){

    }

}
