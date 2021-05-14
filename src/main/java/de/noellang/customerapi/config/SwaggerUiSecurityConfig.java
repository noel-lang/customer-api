package de.noellang.customerapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class SwaggerUiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		auth.inMemoryAuthentication()
				.passwordEncoder(encoder)
				.withUser("admin")
				.password(encoder.encode("1234"))
				.roles("ADMIN");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers(
						"/v3/api-docs/**",
						"/swagger-ui/**",
						"/swagger-resources/**",
						"/swagger-ui.html",
						"/webjars/**",
						"/swagger.json",
						"/configuration/ui"
				)
				.authenticated()
				.and()
				.httpBasic();
	}

}
