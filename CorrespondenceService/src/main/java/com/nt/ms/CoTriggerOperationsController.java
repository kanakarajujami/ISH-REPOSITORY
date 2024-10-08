package com.nt.ms;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nt.bindings.COSummary;
import com.nt.service.CorrespondenseMgmtService;

@RestController
@RequestMapping("/co-trigger-api")
public class CoTriggerOperationsController {
	@Autowired
     private 	CorrespondenseMgmtService service;
	  @GetMapping("/process")
      public ResponseEntity<COSummary>  processAndUpdateTriggers(){
    	  //use service
		  COSummary summary=service.processPendingTriggers();
		  return new ResponseEntity<COSummary>(summary,HttpStatus.OK);
      }
}
