package com.nt.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.bindings.EligibilityDetailsOutput;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.entity.CoTriggersEntity;
import com.nt.entity.DcCasesEntity;
import com.nt.entity.DcChildrenEntity;
import com.nt.entity.DcEducationEntity;
import com.nt.entity.DcIncomeEntity;
import com.nt.entity.EligibilityFDetailsEntity;
import com.nt.entity.PlanEntity;
import com.nt.repository.CitizenAppRegistrationRepository;
import com.nt.repository.DcChildrenRepository;
import com.nt.repository.DcEducationRepositroy;
import com.nt.repository.DcIncomeRepository;
import com.nt.repository.ICoTriggersRepository;
import com.nt.repository.IDcCasesRepository;
import com.nt.repository.IEligibilityDetailsRepository;
import com.nt.repository.IPlanRepository;
@Service
public class EligibilityDeterminatiinServiceImpl implements IEligibilityDeteminationService {
	@Autowired
     private IDcCasesRepository caseRepo;
	@Autowired
	private IPlanRepository planRepo;
	@Autowired
	private DcIncomeRepository incomeRepo;
	@Autowired
	private DcChildrenRepository childrenRepo;
	@Autowired
	private CitizenAppRegistrationRepository citizenRepo;
	@Autowired
	private DcEducationRepositroy educationRepo;
	@Autowired
	private IEligibilityDetailsRepository eligibilityRepo;
	@Autowired
	private ICoTriggersRepository triggerRepo;
	
	@Override
	public EligibilityDetailsOutput detemineEligibility(Integer caseNumber) {
		//get planId and appId
		   Integer appId=null;
		   Integer planId=null;
		   Optional<DcCasesEntity> optCaseEntity=caseRepo.findById(caseNumber);
		   if(optCaseEntity.isPresent()) {
			   DcCasesEntity caseEntity=optCaseEntity.get();
			   appId=caseEntity.getAppId();
			   planId=caseEntity.getPlanId();
		   }
		   
		   //get plan name
		   String planName=null;
		    Optional<PlanEntity>   optPlanEntity=planRepo.findById(planId);
		    if(optPlanEntity.isPresent()) {
		        PlanEntity planEntity=optPlanEntity.get();
		        planName=planEntity.getPlanName(); 
		    }
		    //get citizen age and citizen name
		    Optional<CitizenAppRegistrationEntity> optcitizenEntity=citizenRepo.findById(appId);
		    Integer citizenAge=0;
		    String citizenName=null;
		   Long   citizenSSN=null;;
		    if(optcitizenEntity.isPresent()) {
		    CitizenAppRegistrationEntity citizenEntity=optcitizenEntity.get();
		    	 citizenAge=Period.between(citizenEntity.getDob(), LocalDate.now()).getYears();
		    	 citizenName=citizenEntity.getFullName();
		    	 citizenSSN=citizenEntity.getSsn();
		    	 
		    }
		    
		    //call helper method to plan conditions
		    EligibilityDetailsOutput   elgiOutput= applyPlanContiditions(caseNumber,planName,citizenAge);
		    elgiOutput.setHolderName(citizenName);
		    elgiOutput.setHolderSSN(citizenSSN);
		    //create ElibilityDetails object
		     EligibilityFDetailsEntity eligibilityEntity=new EligibilityFDetailsEntity();
		     eligibilityEntity.setCaseNumber(caseNumber);
		     BeanUtils.copyProperties(elgiOutput, eligibilityEntity);
		     eligibilityRepo.save(eligibilityEntity);
		     //create Trigger Entity
		      CoTriggersEntity triggerEntity=new CoTriggersEntity();
		      triggerEntity.setCaseNumber(caseNumber);
		      triggerEntity.setTriggerStatus("pending");
		      //save trigger entity object
		      triggerRepo.save(triggerEntity);
		      return elgiOutput;
		  
	}//end of method
	    //helper method implementation
	       private EligibilityDetailsOutput applyPlanContiditions(Integer caseNumber,String planName,Integer citizenAge) {
     	   EligibilityDetailsOutput elgiOut=new EligibilityDetailsOutput();
     	   elgiOut.setPlanName(planName);
     	 
     	  //get employee income data
     	   DcIncomeEntity incomeEntity=incomeRepo.findBycaseNumber(caseNumber);
     	   Double empIncome=incomeEntity.getEmpIncome();
     	   Double propertyIncome=incomeEntity.getPropertyIncome();
     	   //for SNAP plan
     	   if(planName.equalsIgnoreCase("SNAP")) {
     	   if(empIncome<=300) {
     		 elgiOut.setPlanStatus("Approved");  
     		 elgiOut.setBenfitAmount(200.0);
     	   }else {
     		   elgiOut.setPlanStatus("Denied");
     		   elgiOut.setDenialReason("High income");
     	   }
     	   }//end of snap plan
     	   //for CCAP plan
     	   else if(planName.equalsIgnoreCase("CCAP")) {
     		  List<DcChildrenEntity> listChildrens= childrenRepo.findByCaseNumber(caseNumber);
     		   boolean kidsCountCondition=false;
     		   boolean kidsAgeCondition=true;
     		   if(!listChildrens.isEmpty()) {
     			  kidsCountCondition=true;
     		   }
     		   for(DcChildrenEntity children: listChildrens) {
     			   Integer kidAge=Period.between(children.getDob(),LocalDate.now()).getYears();
     			   if(kidAge>16) {
     				   kidsAgeCondition=false;
     				   break;
     			   }
     		   }//end for loop
     		    if(empIncome<=300 && kidsAgeCondition && kidsAgeCondition) {
     		    	   elgiOut.setPlanStatus("Approved");
     		    	   elgiOut.setBenfitAmount(300.0);
     		    }else {
     		    	  elgiOut.setPlanStatus("Denied");
     		    	  elgiOut.setDenialReason("ccap plan conditions are not satisfied");
     		    }
     	     
     	   }//end of ccap plan
     	   else if(planName.equalsIgnoreCase("MEDAID")) {
     		      if(empIncome<=300 && propertyIncome==0) {
     		    	   elgiOut.setPlanStatus("Approved");
     		    	   elgiOut.setBenfitAmount(300.0);
     		      }else {
     		    	  elgiOut.setPlanStatus("Denied");
     		    	  elgiOut.setDenialReason("medcaid plan conditions are not satisfied");
     		      }
     	   }//end of medaid plan
     	   else if(planName.equalsIgnoreCase("MEDCARE")) {
     		      if(citizenAge>=65) {
     		    	  elgiOut.setPlanStatus("Approved");
     		    	  elgiOut.setBenfitAmount(300.0);
     		      }else {
     		    	  elgiOut.setPlanStatus("Denied");
     		         elgiOut.setDenialReason("medcare plan conditios are not satisfied ");
     		      }
     	   }//end of medcare plan
     	   else if(planName.equalsIgnoreCase("CAJW")) {
     		    DcEducationEntity educationEntity=educationRepo.findByCaseNumber(caseNumber);
     		    int passOutYear=educationEntity.getPassoutYear();
     		    if(empIncome==0 && passOutYear<LocalDate.now().getYear()) {
     		    	elgiOut.setPlanStatus("Approved");
     		    	elgiOut.setBenfitAmount(300.00);
     		    }else
     		    {
     		    	elgiOut.setPlanStatus("Denied");
     		    elgiOut.setDenialReason("cajw plan conditions are not satisfiied");
     		    }
     	   }//end of cajw
     	   else if(planName.equalsIgnoreCase("QHP")) {
     		      if(citizenAge>=1) {
     		    	   elgiOut.setPlanStatus("Approved");
     		    	   elgiOut.setBenfitAmount(300.0);
     		      }else
     		      {
     		    	 elgiOut.setPlanStatus("Denied");
		    	   elgiOut.setDenialReason("qhp plan conditions are not satisfied");
     		      }
     	   }//end of qhp
     	   //set common properties eligibility object who are plan approved
     	   if(elgiOut.getPlanStatus().equalsIgnoreCase("Approved")) {
     	   elgiOut.setPlanStartDate(LocalDate.now());
     	   elgiOut.setPlanEndDate(LocalDate.now().plusYears(2));
     	   }
     	 return elgiOut;
      }//end of method
      
}
