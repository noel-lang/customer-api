package de.noellang.customerapi.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtTokenProvider {

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expiration}")
	private int jwtExpirationInMs;

	public String generateToken() {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				// .setSubject(Long.toString(userId))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String generateToken(Authentication authentication) {
		// UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return generateToken();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.info("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.info("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.info("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.info("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims string is empty.");
		}

		return false;
	}

}
