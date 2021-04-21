package de.noellang.customerapi.service;

import de.noellang.customerapi.exception.EmailAlreadyExistsException;
import de.noellang.customerapi.exception.ResourceNotFoundException;
import de.noellang.customerapi.model.Customer;
import de.noellang.customerapi.payload.CreateCustomerRequest;
import de.noellang.customerapi.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	public Customer findById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (customer.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		return customer.get();
	}

	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public Customer create(CreateCustomerRequest request) {
		Optional<Customer> existingCustomer = findByEmail(request.getEmail());

		if (existingCustomer.isPresent()) {
			throw new EmailAlreadyExistsException();
		}

		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());

		return customerRepository.save(customer);
	}

}
