package com.nt.ms;

import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.service.IBenfitIssuanceMgmtService;

@RestController
@RequestMapping("/bi-api")
public class BenfitIssuanceOperationsController {
	@Autowired
	private IBenfitIssuanceMgmtService benfitService;
	
	
	@GetMapping("/send")
   public ResponseEntity<String> sendAmount() throws Exception{
	   JobExecution execution=benfitService.sendAmountToBenficiries();
	   return new ResponseEntity<String>(execution.toString(),HttpStatus.OK);
   }
}
