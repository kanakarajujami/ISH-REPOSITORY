package com.nt.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminOperationsAdvice {
	//handle illegal argument exception    
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionInfo> handleIAE(IllegalArgumentException exception) {
		ExceptionInfo info=new ExceptionInfo();
		info.setMessage(exception.getMessage());
		info.setCode(3000);
		 return new ResponseEntity<ExceptionInfo>(info,HttpStatus.OK);
	}
  //handle all exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionInfo> handleAllExceptions(Exception exception){
		 ExceptionInfo info=new ExceptionInfo();
		 info.setMessage(exception.getMessage());
		 info.setCode(3000);
		 return new ResponseEntity<ExceptionInfo>(info,HttpStatus.OK);
	}
}
