package dev.gmarchev.flightmanager.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle cases when the request has failed validation to include the errors in the response
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(
			MethodArgumentNotValidException exception, HttpServletRequest request) {

		List<String> errorMessages = exception.getBindingResult()
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList();

		// Create a custom error response with the default fields and validation details
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				request.getRequestURI(),
				errorMessages);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	record ErrorResponse(LocalDateTime timestamp, int status, String error, String path, List<String> details) {}
}
