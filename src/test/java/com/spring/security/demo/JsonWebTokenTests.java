package com.spring.security.demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
class JsonWebTokenTests {

	@Test
	void dependenciesTest() { // NO Sonnar
		var extraClaims = new HashMap<String, Object>();
		extraClaims.put("organization", "Spring Security");

		Date currentDate = new Date(System.currentTimeMillis());
		Date expirationDate = new Date(currentDate.getTime() + (60 * 1000)); // 1 minute expiration

		var jwt = Jwts.builder()
				.header().type("JWT")
				.and()
				.subject("testJWT")
				.expiration(expirationDate)
				.issuedAt(currentDate)
				.claims(extraClaims)
				.signWith(generateKey(), Jwts.SIG.HS256)
				.compact();

		System.out.println(jwt);
		Assertions.assertNotNull(jwt);
	}

	private SecretKey generateKey() {
		var secretKey = "a-string-secret-at-least-256-bits-long";
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}


}
