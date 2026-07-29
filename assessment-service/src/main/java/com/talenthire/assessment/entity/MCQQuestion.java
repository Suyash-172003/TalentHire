package com.talenthire.assessment.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="mcq_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MCQQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assessment_id",nullable=false)
    private Assessment assessment;


    @Column(nullable=false,length=500)
    private String questionText;


    @Column(nullable=false)
    private String optionA;


    @Column(nullable=false)
    private String optionB;


    @Column(nullable=false)
    private String optionC;


    @Column(nullable=false)
    private String optionD;


    @Column(nullable=false)
    private String correctAnswer;


    private String difficulty;


    private Integer marks;


    private Integer questionOrder;


    private LocalDateTime createdAt;

}