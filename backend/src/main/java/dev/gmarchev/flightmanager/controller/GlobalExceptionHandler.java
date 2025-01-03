package dev.gmarchev.flightmanager.controller;

import java.util.Map;
import java.util.stream.Collectors;

import dev.gmarchev.flightmanager.exceptions.EntityNotFoundException;
import dev.gmarchev.flightmanager.exceptions.InsufficientSeatsException;
import dev.gmarchev.flightmanager.exceptions.IvnalidFlightException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(
			MethodArgumentNotValidException exception, HttpServletRequest request) {

		String errorMessages = exception.getBindingResult()
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining(" "));

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.of("Bad Request", errorMessages));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
			EntityNotFoundException exception, HttpServletRequest request) {

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.of("Not Found", exception.getDetails()));
	}

	@ExceptionHandler(InsufficientSeatsException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientSeats(InsufficientSeatsException e) {

		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(ErrorResponse.of("Insufficient seats", "Няма достатъчно места за полет."));
	}

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<ErrorResponse> handleOptimisticLockingFailure(OptimisticLockingFailureException e) {

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(
						ErrorResponse.of(
								"Optimistic locking failure after multiple retries",
								"Вмомента не може да бъде направена резервация. Моля опитайте отново!"));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleExpiredJwtException(BadCredentialsException e) {

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.of("Bad Credentials", "Невалидни идентификационни данни."));
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.of("Token Expired", "Изтекъл JTW тоукън."));
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException e) {

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.of("Malformed Token", "Невалиден JTW тоукън."));
	}

	@ExceptionHandler(IvnalidFlightException.class)
	public ResponseEntity<ErrorResponse> handleIvnalidFlightException(IvnalidFlightException e) {

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.of("Invalid Flight Data", e.getDetails()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(ErrorResponse.of(
						"Method Not Allowed",
						String.format("The requested method '%s' is not supported", ex.getMethod())));
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
//
//		return ResponseEntity
//				.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(ErrorResponse.of("An unexpected error occurred", "Грешка на сървъра."));
//	}
}
