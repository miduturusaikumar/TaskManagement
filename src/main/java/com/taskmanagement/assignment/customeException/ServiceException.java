package com.taskmanagement.assignment.customeException;


import org.springframework.stereotype.Component;

@Component
public class ServiceException extends RuntimeException{
	
	private String message;

	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public ServiceException(String message) {
		super();
		this.message = message;
	}


	public ServiceException() {
		super();
		
	}
}
