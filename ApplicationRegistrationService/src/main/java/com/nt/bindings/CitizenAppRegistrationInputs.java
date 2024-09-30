package com.nt.bindings;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CitizenAppRegistrationInputs {
  private String fullName;
  private String email;
  private String gender;
  private Long ssn;
  private LocalDate dob;
  private Long phoneNo;
  private Long bankAccNo;
 private String bankName;
}
