package de.noellang.customerapi;

import de.noellang.customerapi.security.JwtTokenProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class CustomerapiApplication implements CommandLineRunner {

	private final JwtTokenProvider jwtTokenProvider;

	public CustomerapiApplication(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerapiApplication.class, args);
	}

	public void run(String... args) {
		String token = jwtTokenProvider.generateToken();
		log.info(token);
	}

}
