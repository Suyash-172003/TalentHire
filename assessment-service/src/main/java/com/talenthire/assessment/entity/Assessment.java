package com.talenthire.assessment.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="assessment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assessment {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer assessmentId;

	    @Column(name="job_id",nullable = false)
	    private Integer jobId;

	    @Column(nullable = false, length = 150)
	    private String title;

	    @Column(length = 500)
	    private String description;

	    @Column(nullable = false)
	    private Integer duration;      
	    
	    @Column(name = "start_time", nullable = true)
	    private LocalDateTime startTime;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "assessment_type", nullable = false)
	    private AssessmentType assessmentType;
	    
	    @Column(name = "end_time", nullable = true)
	    private LocalDateTime endTime;

	    @Column(name = "total_marks")
	    private Integer totalMarks = 0;

	    @Column(name = "pass_marks")
	    private Integer passMarks;

	    @Enumerated(EnumType.STRING)
	    @Column(name="assessment_status",nullable = false)
	    private AssessmentStatus status = AssessmentStatus.DRAFT;
	    
	    @Column(name = "created_by", nullable = false)
	    private Integer createdBy;

	    @CreationTimestamp
	    @Column(name="created_at",updatable = false)
	    private LocalDateTime createdAt;

	    @UpdateTimestamp
	    @Column(name="updated_at")
	    private LocalDateTime updatedAt;

	    @OneToMany(
	            mappedBy = "assessment",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true)
	    private List<CodingQuestion> codingQuestions = new ArrayList<>();
	    
	    @OneToMany(
	            mappedBy = "assessment",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true
	    )
	    private List<MCQQuestion> questions = new ArrayList<>();

	}

