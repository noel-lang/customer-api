package de.noellang.customerapi;

import de.noellang.customerapi.model.Customer;
import de.noellang.customerapi.payload.CreateCustomerRequest;
import de.noellang.customerapi.security.JwtTokenProvider;
import de.noellang.customerapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomerService customerService;

	@Test
	void cantRetrieveCustomersWhenUnauthenticated() throws Exception {
		mockMvc.perform(get("/v1/customer"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

	@Test
	void canRetrieveCustomersWhenAuthenticated() throws Exception {
		mockMvc.perform(
				get("/v1/customer")
						.header("Authorization", "Bearer " + jwtTokenProvider.generateToken()
					)).andExpect(status().isOk());
	}

	@Test
	void canRetrieveSingleCustomer() throws Exception {
		CreateCustomerRequest request = new CreateCustomerRequest();
		request.setFirstName("Max");
		request.setLastName("Mustermann");
		request.setEmail("max.mustermann@example.org");

		Customer customer = customerService.create(request);
		String urlPath = "/v1/customer/" + customer.getId();
		String token = "Bearer " + jwtTokenProvider.generateToken();

		mockMvc.perform(get(urlPath).header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(customer.getLastName()))
				.andExpect(jsonPath("$.email").value(customer.getEmail()));
	}

}
