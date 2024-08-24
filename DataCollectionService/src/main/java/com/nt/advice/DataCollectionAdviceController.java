package com.nt.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nt.exception.InvalidSSNException;

@RestControllerAdvice
public class DataCollectionAdviceController {
	@ExceptionHandler
	public ResponseEntity<String> invalidSSNException(InvalidSSNException exception){
		 return new ResponseEntity<String>(exception.getMessage(),HttpStatus.OK);
	} 
	@ExceptionHandler
    public ResponseEntity<String> handleAllExecetions(Exception exception){
    	  return new ResponseEntity<String>(exception.getMessage(),HttpStatus.OK);
    }
	
}
