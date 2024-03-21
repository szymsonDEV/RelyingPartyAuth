package dev.szymson.relyingpartyauth.u2f;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.AuthenticatorTransport;
import com.yubico.webauthn.data.UserIdentity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.Optional;
import java.util.SortedSet;

@Value
@Builder
@With
public class CredentialRegistration {

    UserIdentity userIdentity;
    Optional<String> credentialNickname;
    SortedSet<AuthenticatorTransport> transports;

    @JsonIgnore Instant registrationTime;
    RegisteredCredential credential;

    Optional<Attestation> attestationMetadata;

    @JsonProperty("registrationTime")
    public String getRegistrationTimestamp() {
        return registrationTime.toString();
    }

    public String getUsername() {
        return userIdentity.getName();
    }
}
