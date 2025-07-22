package com.thmanyah.cms_service.shared.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException{
    private String message;
}
