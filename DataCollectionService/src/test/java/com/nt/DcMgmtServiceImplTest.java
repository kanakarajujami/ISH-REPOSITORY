package com.nt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nt.bindings.PlanSelectionInputs;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.entity.DcCasesEntity;
import com.nt.entity.PlanEntity;
import com.nt.repository.CitizenAppRegistrationRepository;
import com.nt.repository.DcChildrenRepository;
import com.nt.repository.DcEducationRepositroy;
import com.nt.repository.DcIncomeRepository;
import com.nt.repository.IDcCasesRepository;
import com.nt.repository.PlanRepository;
import com.nt.service.DcMgmtServiceImpl;
  @SpringBootTest
  @ExtendWith (MockitoExtension.class)
public class DcMgmtServiceImplTest {
     @MockBean	 
	 private CitizenAppRegistrationRepository citizenRepo;
     @MockBean	 
	 private IDcCasesRepository casesRepo;
     @MockBean	 
	 private PlanRepository planRepo;
     @MockBean	 
	 private DcIncomeRepository incomeRepo;
     @MockBean	 
	 private DcChildrenRepository childrenRepo;
     @MockBean	 
	 private DcEducationRepositroy educationRepo;
     @InjectMocks
     private DcMgmtServiceImpl dcService;
     
     @Test
     public void generateCaseNo() {
    	 CitizenAppRegistrationEntity citizenEntity=new CitizenAppRegistrationEntity() ;
    	 citizenEntity.setAppId(1);
    	Optional<CitizenAppRegistrationEntity> optCitizenEntity=Optional.of(citizenEntity);
    	 //provide DUmmy functionality to findById()
    	 Mockito.when( citizenRepo.findById(1)).thenReturn(optCitizenEntity);
    	 //provide dummy functionality to save()
    	 DcCasesEntity caseEntity1=new DcCasesEntity();
    	 caseEntity1.setAppId(1);
    	 
    	 DcCasesEntity caseEntity2=new DcCasesEntity();
    	 caseEntity2.setCaseNumber(1001);
    	 
    	 Mockito.when(casesRepo.save(caseEntity1)).thenReturn(caseEntity2);
    	 int actual=dcService.generateCaseNumber(1);
    	 Assertions.assertEquals(1001, actual);
    	 
     }
     
     @Test
     public void generateCaseNo2() {
    	 CitizenAppRegistrationEntity citizenEntity=new CitizenAppRegistrationEntity() ;
    	 citizenEntity.setAppId(101);
    	Optional<CitizenAppRegistrationEntity> optCitizenEntity=Optional.empty();
    	 //provide DUmmy functionality to findById()
    	 Mockito.when( citizenRepo.findById(1)).thenReturn(optCitizenEntity);
    	 //provide dummy functionality to save()
    	 DcCasesEntity caseEntity1=new DcCasesEntity();
    	 caseEntity1.setAppId(1);
    	 
    	 DcCasesEntity caseEntity2=new DcCasesEntity();
    	 caseEntity2.setCaseNumber(1001);
    	 
    	 Mockito.when(casesRepo.save(caseEntity1)).thenReturn(caseEntity2);
    	 int actual=dcService.generateCaseNumber(1);
    	 Assertions.assertEquals(0, actual);
    	 
     }
       @Test
     public void showAllPlanNamesTest() {
    	 PlanEntity entity1=new PlanEntity();
    	 entity1.setPlanName("QHP");
    	 PlanEntity entity2=new PlanEntity();
    	 entity2.setPlanName("SNAP");
    	 PlanEntity entity3=new PlanEntity();
    	 entity3.setPlanName("CCAP");
    	 PlanEntity entity4=new PlanEntity();
    	 entity4.setPlanName("`MEDAID");
    	 PlanEntity entity5=new PlanEntity();
    	 entity5.setPlanName("MEDCARE");
    	 PlanEntity entity6=new PlanEntity();
    	 entity6.setPlanName("CAJW");
    	 //prepare list
    	 List<PlanEntity> listPlans=new ArrayList<PlanEntity>();
    	listPlans.add(entity1);listPlans.add(entity2);listPlans.add(entity3);
    	listPlans.add(entity4);listPlans.add(entity5);listPlans.add(entity6);
     
     //provide dummy functionality to findAll()
    	Mockito.when(planRepo.findAll()).thenReturn(listPlans);
    	//invoke showAllPlanNames() to actual result
    	List<String> listPlanNames=dcService.showAllPlanNames();
    	assertEquals(6, listPlanNames.size());
    	
     }
      @Test  
     public void  savePlanSelectionTest() {
    	      	DcCasesEntity caseEntity=new DcCasesEntity();
    	      	caseEntity.setAppId(1001);
    	      	caseEntity.setCaseNumber(1);
    	      	Optional<DcCasesEntity> optCaseEntity=Optional.of(caseEntity);
    	      	//provide dummy functionality  tofindById()
    	      	Mockito.when(casesRepo.findById(1)).thenReturn(optCaseEntity);
    	      	//provide dummy functionality to save()
    	      	DcCasesEntity caseEntity2=new DcCasesEntity();
    	      	caseEntity2.setAppId(1001);
    	      	caseEntity2.setCaseNumber(1);
    	      	caseEntity2.setPlanId(102);
    	      	Mockito.when(casesRepo.save(caseEntity2)).thenReturn(caseEntity2);
    	      	//invoke savePlanSelection(
    	      	 PlanSelectionInputs inputs=new PlanSelectionInputs();
    	      	 inputs.setCaseNumber(1);
    	      	 inputs.setPlanId(102);
    	      	int actual=dcService.savePlanSelection(inputs);
    	      	assertEquals(1, actual);
    	      
     }
      
      
      @Test  
      public void  savePlanSelectionTest2() {
     	      	DcCasesEntity caseEntity=new DcCasesEntity();
     	      	caseEntity.setAppId(1001);
     	      	caseEntity.setCaseNumber(1);
     	      	Optional<DcCasesEntity> optCaseEntity=Optional.empty();
     	      	//provide dummy functionality  tofindById()
     	      	Mockito.when(casesRepo.findById(1)).thenReturn(optCaseEntity);
     	      	//provide dummy functionality to save()
     	      	DcCasesEntity caseEntity2=new DcCasesEntity();
     	      	caseEntity2.setAppId(1001);
     	      	caseEntity2.setCaseNumber(1);
     	      	caseEntity2.setPlanId(102);
     	      	Mockito.when(casesRepo.save(caseEntity2)).thenReturn(caseEntity2);
     	      	//invoke savePlanSelection(
     	      	 PlanSelectionInputs inputs=new PlanSelectionInputs();
     	      	 inputs.setCaseNumber(222);
     	      	 inputs.setPlanId(102);
     	      	int actual=dcService.savePlanSelection(inputs);
     	      	assertEquals(0, actual);
       
       

}
}