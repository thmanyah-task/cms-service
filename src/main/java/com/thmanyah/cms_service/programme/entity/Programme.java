package com.thmanyah.cms_service.programme.entity;


import com.thmanyah.cms_service.episode.entity.Episode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "programme")
public class Programme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @Column
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne
    @JoinColumn(name = "language_id")
    private Language language;
    @Column(columnDefinition = "TEXT")
    private String programmeUrl;
    @Column
    private LocalDate publishedDate;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;

}
