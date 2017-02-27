package com.bit2017.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)
	public String handleExecption(Exception e) {
		
		//log처리
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error(errors.toString());
		
		//2.화면처리( 사용자한테 사과...)
		return "error/exception";
	}
}
