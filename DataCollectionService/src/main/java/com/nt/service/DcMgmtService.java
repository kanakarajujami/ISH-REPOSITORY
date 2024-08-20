package com.nt.service;

import java.util.List;

import com.nt.bindings.ChildrenInputs;
import com.nt.bindings.DcSummaryReport;
import com.nt.bindings.EducationInputs;
import com.nt.bindings.IncomeInputs;
import com.nt.bindings.PlanSelectionInputs;

public interface DcMgmtService {
  public Integer generateCaseNumber(Integer appId);
  public List<String> showAllPlanNames();
  public Integer savePlanSelection(PlanSelectionInputs Inputs);
  public Integer saveIncomeDetails(IncomeInputs income);
  public Integer saveChildrenDetails(List<ChildrenInputs> childrens);
  public Integer saveEducationDetails(EducationInputs education);
  public DcSummaryReport  showSummaryReport(Integer caseNo);
}
