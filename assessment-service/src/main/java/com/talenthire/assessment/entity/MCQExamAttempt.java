package com.talenthire.assessment.entity;

import java.time.LocalDateTime;

import com.talenthire.assessment.entity.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mcq_exam_attempt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MCQExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Assessment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

   
    @Column(nullable = false)
    private Integer candidateId;

  
    @Column(nullable = false)
    private LocalDateTime startTime;

    
    private LocalDateTime endTime;

    
    @Column(nullable = false)
    private Integer totalQuestions;

    
    @Column(nullable = false)
    private Integer totalMarks;

    
    private Integer obtainedMarks;
    
    @Column(name = "result")
    private String result;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamStatus status;

   
    @Column(nullable = false)
    private Boolean submitted;

    private LocalDateTime createdAt;

}