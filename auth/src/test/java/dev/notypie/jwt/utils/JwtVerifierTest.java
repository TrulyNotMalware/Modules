package dev.notypie.jwt.utils;


import dev.notypie.base.SpringIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


@Tag("auth")
@ActiveProfiles("jwt")
public class JwtVerifierTest extends SpringIntegrationTest {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final JwtVerifier verifier;

    @Autowired
    JwtVerifierTest(PrivateKey privateKey, PublicKey publicKey, JwtVerifier verifier){
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.verifier = verifier;
    }

    @Test
    @DisplayName("[app.Auth] JwtVerifier work successfully")
    public void testSignature() throws Exception{
        String plainText = "THIS IS TEST PLAIN TEXT";
        String encrypt = this.verifier.sign(plainText);
        String decrypt = this.verifier.decrypt(encrypt);
        // Asserts Not Null.
        Assertions.assertNotNull(encrypt);
        // Asserts same.
        Assertions.assertEquals(plainText, new String(Base64.getDecoder().decode(decrypt), StandardCharsets.UTF_8));
    }
}