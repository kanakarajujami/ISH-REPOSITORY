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
public class PlanDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planId;
	@Column(length=30)
    private 	String planName;
	@Column
	private LocalDate planStartDate;
	@Column
	private LocalDate planEndDate;
	@Column(length = 50)
	private String description;
	@Column
	private Integer planCategoryId;
	@Column(length=30)
	private String activeSW;
	@CreationTimestamp
    @Column(insertable = true,updatable = false)
	private LocalDate creationDate;
	@UpdateTimestamp
   @Column(updatable = true,insertable = false)
	private LocalDate updationDate;
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
}
