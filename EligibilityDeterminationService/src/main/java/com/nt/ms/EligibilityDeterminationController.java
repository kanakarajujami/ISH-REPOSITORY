package com.nt.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.EligibilityDetailsOutput;
import com.nt.service.IEligibilityDeteminationService;
@RestController
@RequestMapping("ed-api")
public class EligibilityDeterminationController {
   @Autowired	
  private IEligibilityDeteminationService eligibilityService;
 @GetMapping("/determination/{caseNo}")
  public ResponseEntity<EligibilityDetailsOutput> checkCitizenEligibility(Integer caseNumber){
	  //use service
	 EligibilityDetailsOutput elgibilityOut=eligibilityService.detemineEligibility(caseNumber);
	 return new ResponseEntity<EligibilityDetailsOutput>(elgibilityOut,HttpStatus.OK);
  }
}
