package dev.szymson.relyingpartyauth;

import dev.szymson.relyingpartyauth.U2F.InMemoryRegistrationStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
