package dev.szymson.relyingpartyauth.utils;

import com.yubico.webauthn.data.ByteArray;

import java.security.SecureRandom;

/**
 * @author Szymon.Romanowski
 **/
public class RandomGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static ByteArray generateByteArray(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return new ByteArray(bytes);
    }
}
