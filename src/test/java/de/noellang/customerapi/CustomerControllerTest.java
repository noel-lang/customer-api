package de.noellang.customerapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.noellang.customerapi.model.Customer;
import de.noellang.customerapi.payload.CreateCustomerRequest;
import de.noellang.customerapi.repository.CustomerRepository;
import de.noellang.customerapi.security.JwtTokenProvider;
import de.noellang.customerapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

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
	void canCreateSingleCustomer() throws Exception {
		CreateCustomerRequest request = new CreateCustomerRequest();
		request.setFirstName("Max");
		request.setLastName("Mustermann");
		request.setEmail("max.mustermann@example.org");

		String token = "Bearer " + jwtTokenProvider.generateToken();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(request);

		MvcResult result = mockMvc.perform(
				post("/v1/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.header("Authorization", token)
		).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		Customer createdCustomer = mapper.readValue(content, Customer.class);
		String urlPath = "/v1/customer/" + createdCustomer.getId();

		mockMvc.perform(get(urlPath).header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(createdCustomer.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(createdCustomer.getLastName()))
				.andExpect(jsonPath("$.email").value(createdCustomer.getEmail()));
	}

}
