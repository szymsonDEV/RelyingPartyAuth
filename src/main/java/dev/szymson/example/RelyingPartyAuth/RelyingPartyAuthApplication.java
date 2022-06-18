package dev.szymson.example.RelyingPartyAuth;

import dev.szymson.example.RelyingPartyAuth.U2F.InMemoryRegistrationStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class RelyingPartyAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelyingPartyAuthApplication.class, args);
	}

	@Bean
	public InMemoryRegistrationStorage credentialRepository() {
		return new InMemoryRegistrationStorage();
	}
}
