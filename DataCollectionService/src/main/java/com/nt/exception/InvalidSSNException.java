package com.nt.exception;

public class InvalidSSNException extends RuntimeException {
    public InvalidSSNException() {
    	super();
    }
    public InvalidSSNException(String msg) {
    	super(msg);
    }
}
