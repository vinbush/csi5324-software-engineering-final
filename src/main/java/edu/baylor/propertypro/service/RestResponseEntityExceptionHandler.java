package edu.baylor.propertypro.service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.baylor.propertypro.service.serviceexceptions.ServiceException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { ServiceException.class, IllegalArgumentException.class })
	public ResponseEntity<Object> handleServiceException(Exception e, WebRequest request) {
		return new ResponseEntity<Object>(
				e.getMessage(),  HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	ResponseEntity<Object> handleConstraintException(ConstraintViolationException e, WebRequest request) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Validation failed! ");
		
		for (ConstraintViolation v: e.getConstraintViolations()) {
			sb.append("Property ").append(v.getPropertyPath()).append(" ").append(v.getMessage());
		}
		
		return new ResponseEntity<Object>(
				sb.toString(), HttpStatus.BAD_REQUEST);
	}
}
