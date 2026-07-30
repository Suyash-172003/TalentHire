package com.talenthire.job.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="job")
@Getter
@Setter
@NoArgsConstructor
public class Job {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_id")
	private Integer jobId;
	
	@Column(name="recruiter_id")
	private Integer recruiterID=1;
	
	@Column(nullable = false, length = 100)
	private String title;

	@Column(columnDefinition="TEXT")
	private String description;

	@Column(nullable = false, length = 100)
	private String location;
	
	@Column(name ="company_name",nullable = false, length = 100)
	private String companyName;

	private BigDecimal salary;
	
	@Column(name ="shortlist_score",nullable = false)
	private Integer shortlistScore;
	
	
	@Column(name="experience_required",nullable=false)
	private Integer experienceRequired;
	
	@Column(name="vacancies")
	private Integer vacancies;
	
	@Column(name="employment_type",nullable=false)
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JobStatus status=JobStatus.OPEN;
	
	
	@Column(name="work_mode",nullable=false)
	@Enumerated(EnumType.STRING)
	private WorkMode workMode;
	
	
	@Column(name="created_at",nullable=false,updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at",nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	

	@OneToMany(mappedBy="job",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<JobSkill> jobSkills;
	
}
