package de.noellang.customerapi.controller;

import de.noellang.customerapi.model.Customer;
import de.noellang.customerapi.payload.CreateCustomerRequest;
import de.noellang.customerapi.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Liste aller Kunden",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }
			)
	})
	@GetMapping("")
	public ResponseEntity<Page<Customer>> index(Pageable pageable) {
		return ResponseEntity.ok(customerService.findAll(pageable));
	}

	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping("")
	public ResponseEntity<Customer> create(@Valid @RequestBody CreateCustomerRequest request) {
		return ResponseEntity.ok(customerService.create(request));
	}

	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/{id}")
	public ResponseEntity<Customer> show(@PathVariable String id) {
		return ResponseEntity.ok(customerService.findById(id));
	}

	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		customerService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
