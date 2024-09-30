package com.nt.ms;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.ChildrenInputs;
import com.nt.bindings.DcSummaryReport;
import com.nt.bindings.EducationInputs;
import com.nt.bindings.IncomeInputs;
import com.nt.bindings.PlanSelectionInputs;
import com.nt.service.DcMgmtService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dc-api")
@Tag(name="DC-API",description = "Data Collection Module Microservice")
public class DataCollectionOperationsController {
	@Autowired
	private DcMgmtService service;
	@PostMapping("/generateCaseNo/{appId}")
   public ResponseEntity<Integer> generateCaseNo(@PathVariable Integer appId){
	     Integer caseNumber=service.generateCaseNumber(appId);
	     return new ResponseEntity<Integer>(caseNumber,HttpStatus.CREATED);
   }//end of end point
	
	@GetMapping("/allPlans")
	public ResponseEntity<List<String>> getListPlans(){
		List<String> listPlanNames=service.showAllPlanNames();
		return new ResponseEntity<List<String>>(listPlanNames,HttpStatus.OK);
	}//end of end point
	
	@PutMapping("/savePlan")
	public  ResponseEntity<Integer> savePlanSelection(@RequestBody PlanSelectionInputs planInputs){
		   //user service
		Integer caseNumber=service.savePlanSelection(planInputs);
		return new ResponseEntity<Integer>(caseNumber,HttpStatus.CREATED);
		}//end of end point
	
	@PostMapping("/saveChildrens")
	public  ResponseEntity<Integer> saveChildrensData(@RequestBody List<ChildrenInputs> childInputs){
		//user service
		Integer caseNumber=service.saveChildrenDetails(childInputs);
		return new ResponseEntity<Integer>(caseNumber,HttpStatus.CREATED);
	}//end of end point
	
	@PostMapping("/saveIncome")
	public ResponseEntity<Integer> saveIncomeData(@RequestBody IncomeInputs incomeInputs){
		//user service
		Integer caseNumber=service.saveIncomeDetails(incomeInputs);
		return new ResponseEntity<Integer>(caseNumber,HttpStatus.CREATED);
	}//end of end point
	
	@PostMapping("/saveEducation")
	public ResponseEntity<Integer> saveEducationData(@RequestBody EducationInputs educationinputs){
		//use service
		Integer caseNumber=service.saveEducationDetails(educationinputs);
		return new ResponseEntity<Integer>(caseNumber,HttpStatus.CREATED);
	}//end of end point
	
	@GetMapping("/summary/{caseNo}")
	public ResponseEntity<DcSummaryReport> showSummaryReport(@PathVariable Integer caseNo){
		//user service 
		DcSummaryReport summaryReport=service.showSummaryReport(caseNo);
		return new ResponseEntity<DcSummaryReport>(summaryReport,HttpStatus.OK);
	}
}
