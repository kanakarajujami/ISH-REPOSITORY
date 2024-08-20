package com.nt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.bindings.ChildrenInputs;
import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.bindings.DcSummaryReport;
import com.nt.bindings.EducationInputs;
import com.nt.bindings.IncomeInputs;
import com.nt.bindings.PlanSelectionInputs;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.entity.DcCasesEntity;
import com.nt.entity.DcChildrenEntity;
import com.nt.entity.DcEducationEntity;
import com.nt.entity.DcIncomeEntity;
import com.nt.entity.PlanEntity;
import com.nt.repository.CitizenAppRegistrationRepository;
import com.nt.repository.DcChildrenRepository;
import com.nt.repository.DcEducationRepositroy;
import com.nt.repository.DcIncomeRepository;
import com.nt.repository.IDcCasesRepository;
import com.nt.repository.PlanRepository;
@Service("dcServiceImpl")
public class DcMgmtServiceImpl implements DcMgmtService {
 @Autowired	
 private CitizenAppRegistrationRepository citizenRepo;
 @Autowired
 private IDcCasesRepository casesRepo;
 @Autowired
 private PlanRepository planRepo;
 @Autowired
 private DcIncomeRepository incomeRepo;
 @Autowired
 private DcChildrenRepository childrenRepo;
 @Autowired
 private DcEducationRepositroy educationRepo;
	@Override
	public Integer generateCaseNumber(Integer appId) {
	    Optional<CitizenAppRegistrationEntity> opt=citizenRepo.findById(appId); 
	    if(opt.isPresent()) {
	    	DcCasesEntity casesEntity=new DcCasesEntity();
	    	casesEntity.setAppId(appId);
	    	return casesRepo.save(casesEntity).getCaseNumber();
	    }
		return 0;
	}

	@Override
	public List<String> showAllPlanNames() {
		//load all plan details
		List<PlanEntity> listPlanEntities=planRepo.findAll();
		//List<String> listPlanNames=new ArrayList<String>();
		List<String> listPlanNames=listPlanEntities.stream().map(plan->plan.getPlanName()).toList();
		return listPlanNames;
	}

	@Override
	public Integer savePlanSelection(PlanSelectionInputs inputs) {
	  Optional<DcCasesEntity> optCaseEntity=casesRepo.findById(inputs.getCaseNumber());
	  if(optCaseEntity.isPresent()) {
		//  DcCasesEntity casesEntity=new DcCasesEntity();
		  //casesEntity.setPlanId(inputs.getPlanId());
		  DcCasesEntity caseEntity=optCaseEntity.get();
		  caseEntity.setPlanId(inputs.getPlanId());
		  return   casesRepo.save(caseEntity).getCaseNumber();
		  
	  }
		return 0;
	}

	@Override
	public Integer saveIncomeDetails(IncomeInputs income) {
		//convert binding object to entity object
		DcIncomeEntity incomeEntity=new DcIncomeEntity();
		BeanUtils.copyProperties(income, incomeEntity);
		Integer caseNumber=incomeRepo.save(incomeEntity).getCaseNumber();
		return caseNumber;
	}

	@Override
	public Integer saveChildrenDetails(List<ChildrenInputs> listChildrens) {
       //convert binding object to entity object
	   listChildrens.forEach(children->{
		   DcChildrenEntity childrenEntity=new DcChildrenEntity();
		  BeanUtils.copyProperties(children, childrenEntity);
		  childrenRepo.save(childrenEntity);
	   });
	     return listChildrens.get(0).getCaseNumber();
	}

	@Override
	public Integer saveEducationDetails(EducationInputs education) {
		 //convert binding object to entity object
		DcEducationEntity educationEntity=new DcEducationEntity();
		BeanUtils.copyProperties(education, educationEntity);
		Integer caseNumber=educationRepo.save(educationEntity).getCaseNumber();
		return caseNumber;
	}

	@Override
	public DcSummaryReport showSummaryReport(Integer caseNo) {
	  //convert entity objects to bindings objects
		  DcEducationEntity educationEntity=   educationRepo.findByCaseNumber(caseNo);
		  DcIncomeEntity incomeEntity=incomeRepo.findBycaseNumber(caseNo);
		  List<DcChildrenEntity> listChildrens=childrenRepo.findByCaseNumber(caseNo);
		  
		  EducationInputs educationInputs=new EducationInputs();
		  BeanUtils.copyProperties(educationEntity, educationInputs);
		  IncomeInputs incomeInputs=new IncomeInputs();
		  BeanUtils.copyProperties(incomeEntity, incomeInputs);
		  //children list
		  List<ChildrenInputs> ChildrensList=new ArrayList<ChildrenInputs>();
		  listChildrens.forEach(children->{
			  ChildrenInputs childInputs=new ChildrenInputs();
			  BeanUtils.copyProperties(children, childInputs);
			  ChildrensList.add(childInputs);
		  });
		  //getting plan name
		  String planName = null;
		//  Integer appId=null;
		 Optional<DcCasesEntity> optCaseEntity=casesRepo.findById(caseNo);
		 if(optCaseEntity.isPresent()) {
			 Optional<PlanEntity> optPlanEntity=planRepo.findById(optCaseEntity.get().getPlanId());
			 if(optPlanEntity.isPresent()) {
				 planName=optPlanEntity.get().getPlanName();
			 }//inner if
		 }//outer if
		 //collecting citizen data
		 Optional<CitizenAppRegistrationEntity> optCitizenEntity=citizenRepo.findById(optCaseEntity.get().getAppId());
		  CitizenAppRegistrationInputs citizenInputs=new CitizenAppRegistrationInputs();
		  if(optCaseEntity.isPresent()) {
			  BeanUtils.copyProperties(optCitizenEntity.get(), citizenInputs);
		  }
		//set properties to DcSummaryReport
		  DcSummaryReport report=new DcSummaryReport();
		  report.setEducationDetails(educationInputs);
		  report.setIncomeDetails(incomeInputs);
		  report.setChildrenDetails(ChildrensList);
		  report.setPlanName(planName);
		  report.setCitizenAppDetails(citizenInputs);
		  return report;
	}//end of show summary report

}
