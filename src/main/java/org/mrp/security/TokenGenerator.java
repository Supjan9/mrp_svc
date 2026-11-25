package org.mrp.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    private static final int TOKEN_BYTE_LENGTH = 32;
    private final SecureRandom secureRandom;


    public TokenGenerator(SecureRandom secureRandom) {

        this.secureRandom = secureRandom;
    }

    public String generateToken() {
        byte[] randomBytes = new byte[TOKEN_BYTE_LENGTH];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
