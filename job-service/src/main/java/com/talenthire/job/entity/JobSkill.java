package com.talenthire.job.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="job_skill")
@Getter
@Setter
@NoArgsConstructor
public class JobSkill {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_skill_id")
	private Integer jobSkillId;
	
	
	@JoinColumn(name="job_id",nullable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	private Job job;
	
	
	@Column(name="skill_name",nullable=false)
	private String skillName;
}
