package dev.szymson.relyingpartyauth.config;

import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import dev.szymson.relyingpartyauth.U2F.InMemoryRegistrationStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;

/**
 * @author Szymon.Romanowski
 **/
@Configuration
@RequiredArgsConstructor
public class RelyingPartyConfig {

    private final InMemoryRegistrationStorage credentialRepository;

    @Bean
    public RelyingPartyIdentity relyingPartyIdentity() {
        return RelyingPartyIdentity.builder()
                .id("localhost")
                .name("RelyingPartyAuth Application")
                .build();
    }

    @Bean
    public RelyingParty relyingParty(RelyingPartyIdentity rpIdentity) {
        return RelyingParty.builder()
                .identity(rpIdentity)
                .credentialRepository(credentialRepository)
                .allowOriginPort(true)
                .origins(new HashSet<>(List.of("http://localhost:8080")))
                .build();
    }

}
