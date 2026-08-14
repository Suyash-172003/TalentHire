package com.talenthire.application.entity;

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
@Table(
    name = "application"
)
@Getter
@Setter
@NoArgsConstructor
public class Application {
	
	     @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "application_id")
	    private Integer applicationId;

	    @Column(name = "job_id", nullable = false)
	    private Integer jobId;

	    @Column(name = "candidate_id", nullable = false)
	    private Integer candidateId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "resume_id", nullable = false)
	    private Resume resume;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private ApplicationStatus status = ApplicationStatus.APPLIED;

	    @CreationTimestamp
	    @Column(name = "applied_at", nullable = false, updatable = false)
	    private LocalDateTime appliedAt;

	    @UpdateTimestamp
	    @Column(name = "updated_at", nullable = false)
	    private LocalDateTime updatedAt;

	    @OneToMany(
	            mappedBy = "application",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true
	    )
	    private List<Interview> interviews = new ArrayList<>();


}
