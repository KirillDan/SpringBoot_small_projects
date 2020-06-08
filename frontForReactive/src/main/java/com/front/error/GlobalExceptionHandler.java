package com.front.error;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception ex) {
		log.error("service is temporarily unavailable");
		model.addAttribute("cause", ex.getCause().toString());
		return "errorPage";
	}	
	
}