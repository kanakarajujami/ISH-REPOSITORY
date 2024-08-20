package com.nt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="JRTP_DC_CASES")
@Entity
@Data
public class DcCasesEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private  Integer caseNumber;
   private Integer appId;
   private Integer planId;
}
