package com.thmanyah.cms_service.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorModel {
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
