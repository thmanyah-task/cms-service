package com.thmanyah.cms_service.programme.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.programme.entity.Category;
import com.thmanyah.cms_service.programme.entity.Language;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate publishedDate;
    @Pattern(
            regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp|webp))$)",
            message = "Thumbnail must be an image file (jpg, jpeg, png, gif, bmp, webp)"
    )
    private String thumbnail;
    List<EpisodeDto> episodeDtoList;
}
