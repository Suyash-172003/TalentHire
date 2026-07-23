package com.talenthire.assessmentservice.assessment.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.talenthire.assessmentservice.assessment.enums.AssessmentStatus;
import com.talenthire.assessmentservice.assessment.enums.AssessmentType;
import com.talenthire.assessmentservice.mcq.entity.MCQQuestion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assessments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "company_id", nullable = false)
    private Long companyId;

   
    @Column(name = "job_id", nullable = false)
    private Long jobId;

   
    @Column(nullable = false, length = 150)
    private String title;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_type", nullable = false)
    private AssessmentType assessmentType;

   
    @Column(nullable = false)
    private Integer duration;

   
    @Column(name = "pass_marks", nullable = false)
    private Integer passMarks;

    
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentStatus status;

    
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

   
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

   
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MCQQuestion> questions = new ArrayList<>();

}