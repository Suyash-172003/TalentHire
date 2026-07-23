package com.talenthire.application.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_screening")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationScreening {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Integer screeningId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false, unique = true)
    private Application application;

    @Column(name = "match_score", nullable = false)
    private Integer matchScore;

    @Column(name = "matched_skills", columnDefinition = "TEXT")
    private String matchedSkills;

    @Column(name = "missing_skills", columnDefinition = "TEXT")
    private String missingSkills;

    @Column(name = "screening_summary", columnDefinition = "TEXT")
    private String screeningSummary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScreeningStatus status = ScreeningStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "screened_at", nullable = false, updatable = false)
    private LocalDateTime screenedAt;

}
