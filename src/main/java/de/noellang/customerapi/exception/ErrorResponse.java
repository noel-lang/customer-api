package de.noellang.customerapi.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private String error;

	private String message;

}
