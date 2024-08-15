package com.nt.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nt.exceptions.InvalidSSNException;

@RestControllerAdvice
public class CitizenAppRegitrationControllerAdvice {
	@ExceptionHandler(InvalidSSNException.class)
   public ResponseEntity<String> handleInvalidSSN(InvalidSSNException exception){
	   return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	   
   }
	//for handle all execeptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(InvalidSSNException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
