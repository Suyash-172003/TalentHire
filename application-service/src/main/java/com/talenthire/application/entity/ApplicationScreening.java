package com.talenthire.application.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(
	    name = "application_screening"
	)
	@Getter
	@Setter
	@NoArgsConstructor
public class ApplicationScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer screeningId;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(name="match_percentage",nullable=false)
    private Double matchPercentage;

    @Column(name="resume_score",nullable=false)
    private Integer resumeScore;

    @Enumerated(EnumType.STRING)
    @Column(name="screening_status",nullable=false)
    private ScreeningStatus screeningStatus;


    @CreationTimestamp
    @Column(name="created_at",nullable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at",nullable=false)
    private LocalDateTime updatedAt;
}