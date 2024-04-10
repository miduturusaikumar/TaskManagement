package com.taskmanagement.assignment.customeException;

import org.springframework.stereotype.Component;

@Component
public class ControllerException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ControllerException(String message) {
		super();
		this.message = message;
	}

	public ControllerException() {
		super();
		
	}
	

}
