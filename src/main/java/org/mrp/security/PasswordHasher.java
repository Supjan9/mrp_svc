package org.mrp.security;

import de.mkammerer.argon2.Argon2;

public class PasswordHasher {
    private static final int ITERATIONS = 3;
    private static final int MEMORY_KIB = 65536;
    private static final int PARALLELISM = 1;

    private final Argon2 argon2;


    public PasswordHasher(Argon2 argon2) {
        this.argon2 = argon2;
    }

    public String hash(String password) {
        char[] passwordChars = password.toCharArray();
        try {
            return argon2.hash(ITERATIONS, MEMORY_KIB, PARALLELISM, passwordChars);
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }

    public boolean verify(String hash, String password) {
        char[] passwordChars = password.toCharArray();
        try {
            return argon2.verify(hash, passwordChars);
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }
}
