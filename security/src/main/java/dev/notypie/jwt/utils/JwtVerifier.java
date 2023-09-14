package dev.notypie.jwt.utils;

public interface JwtVerifier {
    boolean verifySignature(String plainText, String signature);
    String sign(String plainText);
    public String decrypt(String encryptedMessage);
}
