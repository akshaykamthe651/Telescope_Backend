package com.cybage.wfhreporting.exception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	@SerializedName("status")
	@Expose
	private HttpStatus status;
	@SerializedName("message")
	@Expose
	private String message;

	public GlobalException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
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
		return "GlobalException [status=" + status + ", message=" + message + "]";
	}

}
