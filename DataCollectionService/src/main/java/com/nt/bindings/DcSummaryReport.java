package com.nt.bindings;

import java.util.List;


import lombok.Data;
@Data
public class DcSummaryReport {
  private EducationInputs educationDetails;
  private IncomeInputs incomeDetails;
  private List<ChildrenInputs> childrenDetails;
  private CitizenAppRegistrationInputs CitizenAppDetails;
  private String planName;
}
