package dev.notypie.jwt.utils;


import dev.notypie.base.SpringIntegrationTest;
import dev.notypie.base.SpringMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


@Tag("common")
@ActiveProfiles("jwt")
public class JwtVerifierTest extends SpringIntegrationTest {

    @Autowired
    private PrivateKey privateKey;
    @Autowired
    private PublicKey publicKey;
    @Autowired
    private JwtVerifier verifier;

    @Test
    @DisplayName("[mod.Common] JwtVerifier work successfully")
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