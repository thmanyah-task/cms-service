package com.thmanyah.cms_service.dto;


import com.thmanyah.cms_service.entity.Category;
import com.thmanyah.cms_service.entity.Language;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammeDto {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String subject;
    private String description;
    private Category category;
    private Language language;
    @Pattern(
        regexp = "^https?://www\\.[^\\s/]+\\.com(/.+)$",
        message = "Invalid URL format. Must start with http/https, include www, end with .com, and contain a path"
    )
    private String programmeUrl;



}
