package com.cybage.wfhreporting.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

	public GlobalExceptionHandler() {
		super();
	}

	@ExceptionHandler({ GlobalException.class })
	public ResponseEntity<GlobalExceptionResponse> handleException(GlobalException ex, WebRequest request) {
		GlobalExceptionResponse dpExceptionResponse = new GlobalExceptionResponse();
		dpExceptionResponse.setMessage(ex.getMessage());
		dpExceptionResponse.setStatus(ex.getStatus());
		ex.printStackTrace();
		this.LOGGER.error("GlobalExceptionResponse : " + dpExceptionResponse);
		return new ResponseEntity<>(dpExceptionResponse, dpExceptionResponse.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<GlobalExceptionResponse> handleException(Exception ex) {
		GlobalExceptionResponse dpExceptionResponse = new GlobalExceptionResponse();
		ex.printStackTrace();
		dpExceptionResponse.setMessage(ex.getMessage());
		dpExceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		this.LOGGER.error("GlobalExceptionResponse : " + dpExceptionResponse);
		return new ResponseEntity<>(dpExceptionResponse, dpExceptionResponse.getStatus());
	}

}
