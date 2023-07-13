package com.cybage.wfhreporting.exception;

import org.springframework.http.HttpStatus;

public class GlobalExceptionResponse {

	private HttpStatus status;
	private String message;

	public GlobalExceptionResponse(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public GlobalExceptionResponse() {
		super();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GlobalExceptionResponse [status=" + status + ", message=" + message + "]";
	}

}