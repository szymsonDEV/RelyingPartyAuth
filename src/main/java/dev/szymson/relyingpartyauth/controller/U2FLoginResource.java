package dev.szymson.relyingpartyauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.cache.Cache;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.exception.AssertionFailedException;
import dev.szymson.relyingpartyauth.U2F.AssertionRequestWrapper;
import dev.szymson.relyingpartyauth.U2F.AssertionResponse;
import dev.szymson.relyingpartyauth.utils.JwtTokenUtil;
import dev.szymson.relyingpartyauth.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Szymon.Romanowski
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/u2f/login")
@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
public class U2FLoginResource {

    private final RelyingParty relyingParty;
    private final Cache<ByteArray, AssertionRequestWrapper> assertRequestStorage;
    private final JwtTokenUtil jwtTokenUtil;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @GetMapping("/start")
    public AssertionRequestWrapper startAuthentication2() throws JsonProcessingException {

        AssertionRequestWrapper request =
                new AssertionRequestWrapper(
                        RandomGenerator.generateByteArray(32),
                        relyingParty.startAssertion(StartAssertionOptions.builder().build()));
        assertRequestStorage.put(request.getRequestId(), request);
        return request;

    }

    @PostMapping("/finish")
    public String finishAuthentication(@RequestBody AssertionResponse response) throws IOException {
        AssertionRequestWrapper request = assertRequestStorage.getIfPresent(response.getRequestId());
        if(request == null) {
            throw new IllegalStateException("AssertionRequest not found");
        }

        try {
            AssertionResult result = relyingParty.finishAssertion(FinishAssertionOptions.builder()
                    .request(request.getRequest())
                    .response(response.getCredential())
                    .build());
            if (result.isSuccess()) {
                UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(result.getUsername());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
                String token = jwtTokenUtil.generateToken(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return token;
            }
        } catch (AssertionFailedException e) {
            log.warn(e.getMessage(),e.fillInStackTrace());
            throw new IllegalStateException(e);
        }
        throw new RuntimeException("Authentication failed");
    }
}
