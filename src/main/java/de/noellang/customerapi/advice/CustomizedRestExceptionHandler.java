package de.noellang.customerapi.advice;

import de.noellang.customerapi.exception.EmailAlreadyExistsException;
import de.noellang.customerapi.exception.ErrorResponse;
import de.noellang.customerapi.exception.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomizedRestExceptionHandler {

	@ExceptionHandler({ ResourceNotFoundException.class, EmptyResultDataAccessException.class })
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

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public final ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(Exception ex) {
		ErrorResponse response = ErrorResponse
				.builder()
				.error("EmailAlreadyExists")
				.message("Diese E-Mail existiert bereits")
				.build();

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(response);
	}

	// TODO: InvalidDataAccessApiUsageException
	// -> Falsche Übergabe von Parametern

}
