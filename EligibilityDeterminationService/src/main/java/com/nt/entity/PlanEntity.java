package com.nt.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="JRTP_PLAN_DETAILS")
@Entity
@Data
public class PlanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planId;
	@Column(length = 30)
	private String planName;
	private  LocalDate planStartDate;
	private LocalDate planEndDate;
	@Column(length = 30)
	private String description;
	private Integer planCategoryId;
	@Column(length = 30)
	private  String activeSW;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updationDate;
}
