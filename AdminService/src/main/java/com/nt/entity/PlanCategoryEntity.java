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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="JRTP_PLAN_CATEGORY")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanCategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planCategoryId;
	@Column(length = 30)
	private String categoryName;
	@Column(length=30)
	private String activeSW;
	@CreationTimestamp
	@Column(insertable = true,updatable = false)
	private LocalDate createDate;
	@UpdateTimestamp
	@Column(updatable = true,insertable = false)
	private LocalDate updateDate;
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
}
