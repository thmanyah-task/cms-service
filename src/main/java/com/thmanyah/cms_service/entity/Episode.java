package com.thmanyah.cms_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @Column
    private LocalDate publishedDate;
    @Column
    private Integer episodeNumber;
    @Column
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column
    private Double duration;
    @Column(columnDefinition = "TEXT")
    private String episodeUrl;
    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;
}
