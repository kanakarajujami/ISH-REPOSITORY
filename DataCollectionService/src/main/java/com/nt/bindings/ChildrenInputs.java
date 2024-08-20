package com.nt.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ChildrenInputs {
 public Integer caseNumber;
 public LocalDate dob;
 public Long ssn;
}
