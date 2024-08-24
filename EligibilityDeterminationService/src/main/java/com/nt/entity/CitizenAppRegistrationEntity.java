package com.nt.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="JRTP_CITIZEN_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistrationEntity {
	@Id
	@SequenceGenerator(name="gen1-seq",sequenceName = "app_id_seq",initialValue = 10000,allocationSize = 1)
	@GeneratedValue(generator="gen1-seq",strategy = GenerationType.SEQUENCE)
    private Integer appId;
	@Column(length = 30)
	private String fullName;
	@Column(length = 30)
	private String email;
	@Column(length = 10)
	private String gender;
	@Column
	private Long ssn;
	@Column
	private Long phoneNo;
	@Column(length = 30)
	private String stateName;
	@Column
	private LocalDate dob;
	@Column(length = 30)
	private String createBy;
	@Column(length = 30)
	private String updatedBy;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updationTime;
	
	
}
