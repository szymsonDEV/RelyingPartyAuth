package dev.szymson.relyingpartyauth.U2F;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class RegistrationRequest {

    String username;
    Optional<String> credentialNickname;
    ByteArray requestId;
    PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions;
    String publicKeyCredentialCreationOptionsJSON;
    Optional<ByteArray> sessionToken;
}
