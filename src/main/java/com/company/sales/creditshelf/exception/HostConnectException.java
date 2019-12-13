package com.company.sales.creditshelf.exception;

public class HostConnectException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1005223764822722968L;
	
	HostConnectException(String message){
		super(message);
	}
	public HostConnectException(String message, Throwable cause){
		super(message, cause);
	}
}
