package com.devsuperior.bds02.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds02.services.exceptions.ControllerNotFoundException;
import com.devsuperior.bds02.services.exceptions.DatabaseException;

@ControllerAdvice
public class ControllerExceptionsHandler {

	@ExceptionHandler(ControllerNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				"Entity not found",
				e.getMessage(),
				request.getRequestURI()
				);
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				Instant.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Database violation",
				e.getMessage(),
				request.getRequestURI()
				);
		
		return ResponseEntity.badRequest().body(err);
	}
}
