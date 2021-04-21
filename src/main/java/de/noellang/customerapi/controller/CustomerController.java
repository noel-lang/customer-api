package de.noellang.customerapi.controller;

import de.noellang.customerapi.service.CustomerService;
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

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> index() {
		return ResponseEntity.ok(customerService.findAll());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> show(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.findById(id));
	}

}
