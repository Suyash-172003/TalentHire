package com.talenthire.application.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="resume")
@Getter
@Setter
@NoArgsConstructor
public class Resume {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "resume_id")
	    private Integer resumeId;

	    @Column(name = "candidate_id", nullable = false)
	    private Integer candidateId;

	    @Column(name = "file_name", nullable = false)
	    private String fileName;

	    @Column(name = "file_url", nullable = false)
	    private String fileUrl;

	    @CreationTimestamp
	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    @UpdateTimestamp
	    @Column(name = "updated_at", nullable = false)
	    private LocalDateTime updatedAt;

	    @OneToMany(mappedBy = "resume",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true)
	    private List<ResumeSkill> resumeSkills = new ArrayList<>();

}
