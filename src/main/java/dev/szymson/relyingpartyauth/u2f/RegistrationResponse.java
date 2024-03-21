package dev.szymson.relyingpartyauth.u2f;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import java.util.Optional;
import lombok.Value;

@Value
public class RegistrationResponse {

    private final ByteArray requestId;

    private final PublicKeyCredential<
            AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs>
            credential;

    private final Optional<ByteArray> sessionToken;

    @JsonCreator
    public RegistrationResponse(
            @JsonProperty("requestId") ByteArray requestId,
            @JsonProperty("credential")
                    PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs>
                    credential,
            @JsonProperty("sessionToken") Optional<ByteArray> sessionToken) {
        this.requestId = requestId;
        this.credential = credential;
        this.sessionToken = sessionToken;
    }
}
