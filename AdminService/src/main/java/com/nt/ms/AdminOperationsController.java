package com.nt.ms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.PlanDetailsInputs;
import com.nt.bindings.PlanDetailsOutput;
import com.nt.service.IAdminMgmtService;
@RestController
@RequestMapping("/admin-api")
public class AdminOperationsController {
  @Autowired	
  private IAdminMgmtService adminService;
  @PostMapping("/savePlan")
  public ResponseEntity<String> savePlan( @RequestBody PlanDetailsInputs planInputs){
	 //user service
	 String resultMsg=adminService.registerPlan(planInputs); 
	 return new ResponseEntity<String>(resultMsg,HttpStatus.CREATED);
  }
  
   @GetMapping("/allCategories")
  public ResponseEntity<Map<Object,String>> showAllPlanCategories(){
	//user service
	  Map<Object,String> categoriesMap=adminService.getAllPlanCategories();
	  return new ResponseEntity<Map<Object,String>>(categoriesMap,HttpStatus.OK);
  }
   
   @GetMapping("/allPlans")
   public ResponseEntity<List<PlanDetailsOutput>> showAllPlans(){
	   //user service
	   List<PlanDetailsOutput> listPlanOut=adminService.getAllPlanDetails();
	   return new ResponseEntity<List<PlanDetailsOutput>>(listPlanOut,HttpStatus.OK);
   }
   
   @GetMapping("/find/{planId}")
   public ResponseEntity<PlanDetailsOutput> showPlanById(@PathVariable Integer planId){
	   //use service
	   PlanDetailsOutput planOut=adminService.showPlanById(planId);
	   return new ResponseEntity<PlanDetailsOutput>(planOut,HttpStatus.OK);
   }
   
   @PutMapping("/update")
   public ResponseEntity<String> updatePlan(@RequestBody PlanDetailsOutput planOut){
	    //use service
	    String resultMsg=adminService.updatePlan(planOut);
	    return new ResponseEntity<String>(resultMsg,HttpStatus.OK);
   }
   
   @PatchMapping("/update/{planId}/{status}")
   public ResponseEntity<String> updatePlanStatusById(@PathVariable Integer planId,@PathVariable String status){
	  //use status
	  String resultMsg=adminService.changePlanStatusById(planId, status) ;
	  return new ResponseEntity<String>(resultMsg,HttpStatus.OK);
   }
}
