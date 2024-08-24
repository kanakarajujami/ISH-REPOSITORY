package com.nt.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-web-api")
public class SSAWebOperationsController {
	 @GetMapping("/find/{ssa}")
     public ResponseEntity<String> getStateBySSA(@PathVariable Long ssa){
    	    if(String.valueOf(ssa).length()!=9) 
    	    	return new ResponseEntity<String>("invalid ssn",HttpStatus.BAD_REQUEST);
    	      
    	    //get state code
    	    Long stateCode=ssa%100;
    	    String stateName=null;
    	    if(stateCode==01) 
    	     	   stateName="Wasington DC";
    	    else if(stateCode==02) 
    	    	   stateName="Ohio";
    	    else if(stateCode==03)
    	           stateName="Texas";
    	    else if(stateCode==04)
    	    	   stateName="California";
    	    else if(stateCode==05) 
    	    	    stateName="Florida";
    	    else
    	    	    stateName="invalid ssn";
    	        return new ResponseEntity<String>(stateName,HttpStatus.OK);
     }
}
