package com.nt.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="JRTP_ELIGIBILITY_DETAILS")
public class EligibilityFDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eDTraceId;
	@Column(length=30)
	private Integer  caseNumber;
	private  String holderName;
	private  Long holderSSN;
	@Column(length=30)
	private String planName;
	@Column(length=30)
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benfitAmount;
	private Long bankAccNo;
	@Column(length = 30)
	private String bankName;
	@Column(length=50)
	private  String denialReason;
}
