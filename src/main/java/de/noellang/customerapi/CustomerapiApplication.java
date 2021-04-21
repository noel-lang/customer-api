package de.noellang.customerapi;

import de.noellang.customerapi.security.JwtTokenProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
		System.out.println(token);
	}

}
