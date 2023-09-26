package dev.notypie.jwt.utils;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtVerifier {
    boolean verifySignature(String plainText, String signature);
    String sign(String plainText);
    String decrypt(String encryptedMessage);
    Map<String, Object> userParser(Claims claims);
}
