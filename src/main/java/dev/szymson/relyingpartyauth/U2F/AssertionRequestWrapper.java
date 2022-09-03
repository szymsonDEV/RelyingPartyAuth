package dev.szymson.relyingpartyauth.U2F;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialRequestOptions;
import java.util.Optional;
import lombok.NonNull;
import lombok.Value;

@Value
public class AssertionRequestWrapper {

    @NonNull private final ByteArray requestId;

    @NonNull private final PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions;

    @NonNull private final String publicKeyCredentialRequestOptionsJSON;

    @NonNull private final Optional<String> username;

    @NonNull @JsonIgnore private final transient com.yubico.webauthn.AssertionRequest request;

    public AssertionRequestWrapper(
            @NonNull ByteArray requestId, @NonNull com.yubico.webauthn.AssertionRequest request) throws JsonProcessingException {
        this.requestId = requestId;
        this.publicKeyCredentialRequestOptions = request.getPublicKeyCredentialRequestOptions();
        this.publicKeyCredentialRequestOptionsJSON = request.getPublicKeyCredentialRequestOptions().toCredentialsGetJson();
        this.username = request.getUsername();
        this.request = request;
    }
}