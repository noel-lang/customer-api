package de.noellang.customerapi.controller;

import de.noellang.customerapi.model.Customer;
import de.noellang.customerapi.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(
			summary = "Liste alle Kunden auf.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Liste aller Kunden",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }
			)
	})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> index(Pageable pageable) {
		return ResponseEntity.ok(customerService.findAll(pageable));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> show(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.findById(id));
	}

}
