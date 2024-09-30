package com.nt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.bindings.ChildrenInputs;
import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.bindings.DcSummaryReport;
import com.nt.bindings.EducationInputs;
import com.nt.bindings.IncomeInputs;
import com.nt.bindings.PlanSelectionInputs;
import com.nt.ms.DataCollectionOperationsController;
import com.nt.service.DcMgmtService;

@WebMvcTest(value=DataCollectionOperationsController.class)
public class DataCollectionOperationsControllerTest {
    @MockBean //create mock bean for DcMgmtService object
    private DcMgmtService service;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void showAllPlansTest() throws Exception{
    	//prepare list plans
    	 List<String> listPlans=List.of("SNAP", "CCAP", "CAJW", "MEDAID", "MEDCARE", "QHP");
    	 //provide dummy functionality to getListPlans() method
    	 Mockito.when(service.showAllPlanNames()).thenReturn(listPlans);
    	 //prepare request builder
    	 MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.get("/dc-api/allPlans");
    	 //generate request and get the result
    	 MvcResult result=mockMvc.perform(builder).andReturn();
    	 //get response content or status code
    	 Integer status=result.getResponse().getStatus();
    	 Assertions.assertEquals(200, status);
    	 
    }
      @Test
      public void generateCaseNoTest1()throws Exception{
    	//provide dummy functionality service object generateCaseNo() method
    	  Mockito.when(service.generateCaseNumber(1)).thenReturn(10011);//giving correct case number
    	  //prepare request builder object
    	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.post("/dc-api/generateCaseNo/1");
    	  //generate request and get result
    	  MvcResult result=mockMvc.perform(builder).andReturn();
    	  //get response content or status code
    	  String responseContent=result.getResponse().getContentAsString();
    	  Assertions.assertEquals(10011, Integer.parseInt(responseContent));
    	  
     }
      
      @Test
      public void generateCaseNoTest2()throws Exception{
    	//provide dummy functionality service object generateCaseNo() method
    	  Mockito.when(service.generateCaseNumber(123)).thenReturn(0);//giving wrong case Number
    	  //prepare request builder object
    	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.post("/dc-api/generateCaseNo/1");
    	  //generate request and get result
    	  MvcResult result=mockMvc.perform(builder).andReturn();
    	  //get response content or status code
    	  String responseContent=result.getResponse().getContentAsString();
    	  Assertions.assertEquals(0, Integer.parseInt(responseContent));
    	  
     }
       @Test
      public void savePlanSelectionTest1() throws Exception{
    	   //prepare plan selection inputs object data
    	   PlanSelectionInputs inputs=new PlanSelectionInputs();
    	   inputs.setCaseNumber(1);
    	   inputs.setPlanId(1000);
    	   //provide dummy service object savePlanSelection() method
    	   Mockito.when(service.savePlanSelection(inputs)).thenReturn(1);
    	   //prepare inputs object to json
    	   ObjectMapper mapper=new ObjectMapper();
    	   String jsonContent=mapper.writeValueAsString(inputs);
    	   //prepare request builder object
     	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.put("/dc-api/savePlan")
     			                                                                            .contentType("application/json").content(jsonContent);                                                                  
     	  //generate request and get result
     	  MvcResult result=mockMvc.perform(builder).andReturn();
     	  //get response content or status code
     	  String responseContent=result.getResponse().getContentAsString();
     	  Assertions.assertEquals(1, Integer.parseInt(responseContent));
    	   
      }
       
       
       @Test
       public void savePlanSelectionTest2() throws Exception{
     	   //prepare plan selection inputs object data
     	   PlanSelectionInputs inputs=new PlanSelectionInputs();//give wrong case number
     	   inputs.setCaseNumber(124);
     	   inputs.setPlanId(1000);
     	   //provide dummy service object savePlanSelection() method
     	   Mockito.when(service.savePlanSelection(inputs)).thenReturn(0);
     	   //prepare inputs object to json
     	   ObjectMapper mapper=new ObjectMapper();
     	   String jsonContent=mapper.writeValueAsString(inputs);
     	   //prepare request builder object
      	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.put("/dc-api/savePlan")
      			                                                                            .contentType("application/json").content(jsonContent);                                                                  
      	  //generate request and get result
      	  MvcResult result=mockMvc.perform(builder).andReturn();
      	  //get response content or status code
      	  String responseContent=result.getResponse().getContentAsString();
      	  Assertions.assertEquals(0, Integer.parseInt(responseContent));
     	   
       }
       
       @Test
       public void saveIncomeDetailsTest() throws Exception {
    	   //prepare income object data
    	   IncomeInputs inputs=new IncomeInputs();
    	   inputs.setCaseNumber(1);inputs.setEmpIncome(250.0);inputs.setPropertyIncome(500.0);
    	   //provide dummy service object savePlanSelection() method
     	   Mockito.when(service.saveIncomeDetails(inputs)).thenReturn(1);
     	   //prepare inputs object to json
     	   ObjectMapper mapper=new ObjectMapper();
     	   String jsonContent=mapper.writeValueAsString(inputs);
     	   //prepare request builder object
      	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.post("/dc-api/saveIncome")
      			                                                                            .contentType("application/json").content(jsonContent);                                                                  
      	  //generate request and get result
      	  MvcResult result=mockMvc.perform(builder).andReturn();
      	  //get response content or status code
      	  Integer status=result.getResponse().getStatus();
      	  Assertions.assertEquals(201, status);
    	  
       }
       
       @Test 
       public void saveEducationDetailsTest() throws Exception {
    	   //prepare income object data
    	   EducationInputs inputs=new EducationInputs();
    	   inputs.setCaseNumber(1);inputs.setHighestQlf("B.Tech");inputs.setPassoutYear(2021);
    	   //provide dummy service object savePlanSelection() method
     	   Mockito.when(service.saveEducationDetails(inputs)).thenReturn(1);
     	   //prepare inputs object to json
     	   ObjectMapper mapper=new ObjectMapper();
     	   String jsonContent=mapper.writeValueAsString(inputs);
     	   //prepare request builder object
      	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.post("/dc-api/saveEducation")
      			                                                                            .contentType("application/json").content(jsonContent);                                                                  
      	  //generate request and get result
      	  MvcResult result=mockMvc.perform(builder).andReturn();
      	  //get response content or status code
      	  Integer status=result.getResponse().getStatus();
      	  Assertions.assertEquals(201, status);
    	  
       }
       @Test
       public void saveChidrenDetailsTest() throws Exception {
    	   //prepare list education inputs
    	   ChildrenInputs inputs1=new ChildrenInputs();
    	   inputs1.setCaseNumber(1);inputs1.setDob(LocalDate.of(2001, 8, 18));inputs1.setSsn(645473823L);
    	   ChildrenInputs inputs2=new ChildrenInputs();
    	   inputs2.setCaseNumber(1);inputs2.setDob(LocalDate.of(1997, 12, 24));inputs1.setSsn(780473823L);
    	   List<ChildrenInputs> listChildrens=new ArrayList<ChildrenInputs>();
    	   listChildrens.add(inputs1);listChildrens.add(inputs2);
    	   //provide dummy service object savePlanSelection() method
     	   Mockito.when(service.saveChildrenDetails(listChildrens)).thenReturn(1);
    	 //prepare inputs object to json
     	   ObjectMapper mapper=new ObjectMapper();
     	   String jsonContent=mapper.writeValueAsString(listChildrens);
     	   //prepare request builder object
      	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.post("/dc-api/saveChildrens")
      			                                                                            .contentType("application/json").content(jsonContent);                                                                  
      	  //generate request and get result
      	  MvcResult result=mockMvc.perform(builder).andReturn();
      	  //get response content or status code
      	  Integer status=result.getResponse().getStatus();
      	  Assertions.assertEquals(201, status);

    	   
       }
    @Test
    public void showSummaryReportTest() throws Exception {
    	//prepare income object data
 	   IncomeInputs inputs1=new IncomeInputs();
 	   inputs1.setCaseNumber(1);inputs1.setEmpIncome(250.0);inputs1.setPropertyIncome(500.0);
 	   //prepare income object data
	   EducationInputs inputs2=new EducationInputs();
	   inputs2.setCaseNumber(1);inputs2.setHighestQlf("B.Tech");inputs2.setPassoutYear(2021);
	 //prepare list education inputs
	   ChildrenInputs inputs3=new ChildrenInputs();
	   inputs3.setCaseNumber(1);inputs3.setDob(LocalDate.of(2001, 8, 18));inputs3.setSsn(645473823L);
	   ChildrenInputs inputs4=new ChildrenInputs();
	   inputs4.setCaseNumber(1);inputs4.setDob(LocalDate.of(1997, 12, 24));inputs4.setSsn(780473823L);
	   List<ChildrenInputs> listChildrens=new ArrayList<ChildrenInputs>();
	   listChildrens.add(inputs3);listChildrens.add(inputs4);
	   //prepare citizen application inputs
	   CitizenAppRegistrationInputs citizenInputs=new CitizenAppRegistrationInputs();
	   citizenInputs.setFullName("raju");
	   citizenInputs.setEmail("raju@123");
	   citizenInputs.setStateName("california");
	   citizenInputs.setGender("male");
	   citizenInputs.setPhoneNo(748368373L);
	   citizenInputs.setSsn(564764804L);
	   citizenInputs.setDob(LocalDate.of(1994, 8, 9));
	   //prepare summary object
	   DcSummaryReport report=new DcSummaryReport();
	   report.setIncomeDetails(inputs1);
	   report.setEducationDetails(inputs2);
	   report.setChildrenDetails(listChildrens);
	   report.setCitizenAppDetails(citizenInputs);
	   report.setPlanName("SNAP");
	   //provide dummy service object savePlanSelection() method
 	   Mockito.when(service.showSummaryReport(1)).thenReturn(report);
	 //prepare inputs object to json
 	   ObjectMapper mapper=new ObjectMapper();
 	   String jsonContent=mapper.writeValueAsString(listChildrens);
 	   //prepare request builder object
  	  MockHttpServletRequestBuilder builder=MockMvcRequestBuilders.get("/dc-api/summary/1");
  			                                                                                                                                             
  	  //generate request and get result
  	  MvcResult result=mockMvc.perform(builder).andReturn();
  	  //get response content or status code
  	  Integer status=result.getResponse().getStatus();
  	  Assertions.assertEquals(200, status);

	   
	   
    }  
	
}
