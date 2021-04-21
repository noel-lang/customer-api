package de.noellang.customerapi;

import de.noellang.customerapi.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Test
	public void shouldReturn403Forbidden() throws Exception {
		mockMvc.perform(get("/v1/customer"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

	@Test
	public void shouldReturn200Ok() throws Exception {
		mockMvc.perform(
				get("/v1/customer")
						.header("Authorization", "Bearer " + jwtTokenProvider.generateToken()
					)).andExpect(status().isOk());
	}

}
