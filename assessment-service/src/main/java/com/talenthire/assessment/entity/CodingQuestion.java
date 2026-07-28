package com.talenthire.assessment.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="coding_question")
@Getter
@Setter
@NoArgsConstructor
public class CodingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codingQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name="problem_statement",nullable = false, columnDefinition = "TEXT")
    private String problemStatement;

    @Column(nullable = false)
    private Integer marks;

    @Column(name="question_order",nullable = false)
    private Integer questionOrder;

    @CreationTimestamp
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "codingQuestion",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CodingTestCase> testCases = new ArrayList<>();

}
