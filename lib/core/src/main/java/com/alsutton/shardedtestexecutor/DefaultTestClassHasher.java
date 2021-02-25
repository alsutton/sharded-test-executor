package com.alsutton.shardedtestexecutor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The default implementation uses the JRE provided SHA-1 hashing
 * implementation. It's widely available, quick, and for any given
 * string will produce the same value each time on all JREs.
 */
public class DefaultTestClassHasher implements TestClassHasher {

    private static final String DIGEST_ALGORITHM = "SHA-1";

    // ThreadLocal so we can avoid recreating a MessageDigest for each class.
    private ThreadLocal<MessageDigest> digesterCache = ThreadLocal.withInitial(this::getDigester);

    @Override
    public int hash(String testClassName) {
        MessageDigest digester = digesterCache.get();
        digester.reset();
        byte[] digest = digester.digest(testClassName.getBytes(StandardCharsets.UTF_16));
        // Convert lowest 4 bytes into an int
        return (digest[3] << 24) | (digest[2] << 16) | (digest[1] << 8) | digest[0];
    }

    private MessageDigest getDigester(){
        try {
            return MessageDigest.getInstance(DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to find implementation of requested digest algorithm.", e);
        }
    }
}
