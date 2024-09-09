package com.nt.service;

import java.util.List;
import java.util.Map;

import com.nt.bindings.PlanDetailsInputs;
import com.nt.bindings.PlanDetailsOutput;

public interface IAdminMgmtService {
    public String registerPlan(PlanDetailsInputs planInputs) ;
    public Map<Object,String> getAllPlanCategories();
    public List<PlanDetailsOutput> getAllPlanDetails();  
    public PlanDetailsOutput showPlanById(Integer planId);
    public  String updatePlan(PlanDetailsOutput planOut);
    public   String changePlanStatusById(Integer planId,String status);
    
}
