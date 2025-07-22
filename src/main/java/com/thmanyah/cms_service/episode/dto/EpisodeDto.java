package com.thmanyah.cms_service.episode.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thmanyah.cms_service.programme.entity.Programme;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDto {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate publishedDate;
    private Integer episodeNumber;
    private String subject;
    private String description;
    @Positive(message = "Duration must be greater than 0")
    private Double duration;
    @Pattern(
            regexp = "^https?://www\\.[^\\s/]+\\.com(/.+)$",
            message = "Invalid URL format. Must start with http/https, include www, end with .com, and contain a path"
    )
    private String episodeUrl;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Programme programme;


    public EpisodeDto(Long id, LocalDateTime createdDate, LocalDateTime updatedDate, LocalDate publishedDate, Integer episodeNumber, String subject, String description, Double duration, String episodeUrl) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.publishedDate = publishedDate;
        this.episodeNumber = episodeNumber;
        this.subject = subject;
        this.description = description;
        this.duration = duration;
        this.episodeUrl = episodeUrl;
    }

}
