package dev.szymson.relyingpartyauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.cache.Cache;
import com.yubico.webauthn.*;
import com.yubico.webauthn.data.*;
import com.yubico.webauthn.exception.RegistrationFailedException;
import dev.szymson.relyingpartyauth.u2f.CredentialRegistration;
import dev.szymson.relyingpartyauth.u2f.InMemoryRegistrationStorage;
import dev.szymson.relyingpartyauth.u2f.RegistrationRequest;
import dev.szymson.relyingpartyauth.u2f.RegistrationResponse;
import dev.szymson.relyingpartyauth.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

/**
 * @author Szymon.Romanowski
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/u2f/register")
@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
public class U2FRegisterResource {

    private final RelyingParty relyingParty;
    private final Cache<ByteArray, RegistrationRequest> registerRequestStorage;
    private final InMemoryRegistrationStorage credentialRepository;
    private final Clock clock = Clock.systemDefaultZone();
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @GetMapping("/start")
    public RegistrationRequest startRegistration(@RequestParam("username") String username) throws JsonProcessingException {
        final Collection<CredentialRegistration> registrations =
                credentialRepository.getRegistrationsByUsername(username);
        final Optional<UserIdentity> existingUser =
                registrations.stream().findAny().map(CredentialRegistration::getUserIdentity);

        if(existingUser.isEmpty()) {
            final UserIdentity registrationUserId =
                    existingUser.orElseGet(
                            () ->
                                    UserIdentity.builder()
                                            .name(username)
                                            .displayName(username)
                                            .id(RandomGenerator.generateByteArray(32))
                                            .build());


            PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions = relyingParty.startRegistration(
                    StartRegistrationOptions.builder()
                            .user(registrationUserId)
                            .authenticatorSelection(
                                    AuthenticatorSelectionCriteria.builder()
                                            .residentKey(ResidentKeyRequirement.REQUIRED)
                                            .build())
                            .build());

            RegistrationRequest request =
                    new RegistrationRequest(
                            username,
                            Optional.of(username),
                            RandomGenerator.generateByteArray(32),
                            publicKeyCredentialCreationOptions,
                            publicKeyCredentialCreationOptions.toCredentialsCreateJson(),
                            null);


            registerRequestStorage.put(request.getRequestId(), request);

            return request;
        }

        throw new IllegalStateException("User already registred");
    }

    @PostMapping("/finish")
    public void finishRegistration(@RequestBody RegistrationResponse response) {

        RegistrationRequest request = registerRequestStorage.getIfPresent(response.getRequestId());
        if(request == null) {
            throw new IllegalStateException("RegistrationRequest not found");
        }

        try {
            RegistrationResult result = relyingParty.finishRegistration(FinishRegistrationOptions.builder()
                    .request(request.getPublicKeyCredentialCreationOptions())
                    .response(response.getCredential())
                    .build());

            UserIdentity userIdentity = request.getPublicKeyCredentialCreationOptions().getUser();
            credentialRepository.addRegistrationByUsername(request.getUsername(),
                    CredentialRegistration.builder()
                            .userIdentity(userIdentity)
                            .credentialNickname(request.getCredentialNickname())
                            .registrationTime(clock.instant())
                            .credential(RegisteredCredential.builder()
                                    .credentialId(result.getKeyId().getId())
                                    .userHandle(userIdentity.getId())
                                    .publicKeyCose(result.getPublicKeyCose())
                                    .signatureCount(result.getSignatureCount())
                                    .build())
                            .transports(result.getKeyId().getTransports().orElseGet(TreeSet::new))
//                            .attestationMetadata(Attestation.builder().metadataIdentifier(request.getPublicKeyCredentialCreationOptions().getAttestation().)
                            .build());
        } catch (RegistrationFailedException e) {
            log.warn(e.getMessage(),e.fillInStackTrace());
            throw new IllegalStateException(e);
        }
    }

}
