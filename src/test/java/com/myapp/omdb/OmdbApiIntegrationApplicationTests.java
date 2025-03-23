package com.myapp.omdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OmdbApiIntegrationApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext, "Application context should not be null");
	}

	@Test
	void passwordEncoderIsConfigured() {
		assertNotNull(passwordEncoder, "Password encoder should be configured");
		String rawPassword = "testPassword123";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		assertNotEquals(rawPassword, encodedPassword, "Password should be encoded");
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Password should match");
	}

	@Test
	void securityBeansAreLoaded() {
		assertNotNull(applicationContext.getBean("securityFilterChain"), "Security filter chain should be configured");
		assertNotNull(applicationContext.getBean("authenticationProvider"), "Authentication provider should be configured");
	}

}
