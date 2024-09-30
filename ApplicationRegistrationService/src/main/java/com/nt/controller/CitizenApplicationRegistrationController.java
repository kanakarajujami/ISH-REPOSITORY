package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.service.ICitizenApplicationRegistrationService;

@RestController
@RequestMapping("/citizenAR-api")
public class CitizenApplicationRegistrationController {
	@Autowired
	private ICitizenApplicationRegistrationService registrationService;
    @PostMapping("/save")	
   public ResponseEntity<String> citizenApplicationRegistration(@RequestBody CitizenAppRegistrationInputs inputs) throws Exception{
	     
	    	   Integer appId=registrationService.citizenRegistrationApplication(inputs);
	    		return new ResponseEntity<String>("citizen Application regitered with id:"+appId,HttpStatus.OK);
	    	   
   }//end method
}//class
