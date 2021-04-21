package de.noellang.customerapi.advice;

import de.noellang.customerapi.exception.ErrorResponse;
import de.noellang.customerapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomizedRestExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleResourceNotFound(Exception ex) {
		ErrorResponse response = ErrorResponse
				.builder()
				.error("ResourceNotFound")
				.message("Die angefragte Resource konnte nicht gefunden werden")
				.build();

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

}
