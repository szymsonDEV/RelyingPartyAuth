package dev.szymson.relyingpartyauth.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yubico.webauthn.data.ByteArray;
import dev.szymson.relyingpartyauth.U2F.AssertionRequestWrapper;
import dev.szymson.relyingpartyauth.U2F.RegistrationRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Szymon.Romanowski
 **/
@Configuration
public class CacheConfig {

    @Bean
    public Cache<ByteArray, RegistrationRequest> registerRequestStorage() {
        return CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    public Cache<ByteArray, AssertionRequestWrapper> assertRequestStorage() {
        return CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }
}
