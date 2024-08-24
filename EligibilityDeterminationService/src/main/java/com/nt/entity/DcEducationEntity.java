package com.nt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="JRTP_DC_EDUCATION")
@Entity
public class DcEducationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer educationId;
	private Integer caseNumber;
	private String highestQlf;
	private Integer passoutYear;
}
