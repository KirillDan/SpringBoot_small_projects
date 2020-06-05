package com.orkestr.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Model model, Exception ex) {
		log.error("service is temporarily unavailable");
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCause(ex.getCause().toString());
		errorMessage.setErrorName("Сервис временно не доступен");
		return ResponseEntity.ok(errorMessage);
	}	
	
}