package com.thmanyah.cms_service.shared.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorModel {
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
