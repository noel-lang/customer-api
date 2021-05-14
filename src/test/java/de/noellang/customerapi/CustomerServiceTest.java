package de.noellang.customerapi;

import de.noellang.customerapi.model.Customer;
import de.noellang.customerapi.payload.CreateCustomerRequest;
import de.noellang.customerapi.repository.CustomerRepository;
import de.noellang.customerapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	CustomerService customerService;

	void canFindCustomerById() {

	}

	void throwResourceNotFoundError() {

	}

	@Test
	void canCreateCustomer() {
		CreateCustomerRequest request = new CreateCustomerRequest();
		request.setFirstName("Max");
		request.setLastName("Mustermann");
		request.setEmail("max.mustermann@example.org");

		Customer mockCustomer = new Customer();
		mockCustomer.setFirstName(request.getFirstName());
		mockCustomer.setLastName(request.getLastName());
		mockCustomer.setEmail(request.getEmail());

		Mockito.when(customerService.create(request)).thenReturn(mockCustomer);

		Customer customer = customerService.create(request);

		assertNotNull(customer);
		assertSame(customer.getFirstName(), request.getFirstName());
		assertSame(customer.getLastName(), request.getLastName());
		assertSame(customer.getEmail(), request.getEmail());
	}

	void throwEmailAlreadyExistsError() {

	}

}
