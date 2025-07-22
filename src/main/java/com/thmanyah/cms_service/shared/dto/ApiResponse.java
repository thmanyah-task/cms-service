package com.thmanyah.cms_service.shared.exception.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private T data;
}
