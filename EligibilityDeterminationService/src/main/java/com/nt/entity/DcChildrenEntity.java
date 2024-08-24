package com.nt.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="JRTP_DC_CHILDREN")
@Data
public class DcChildrenEntity {
   @Id	
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer childrenId;
   private Integer caseNumber;
   private LocalDate dob;
   private Long ssn;
}
