package dev.notypie.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

//https://gist.github.com/IMS94/588e7bee646423308c23bfbff7681c94
@Slf4j
@Configuration
public class JwtConfiguration {

    @Value("${token.jwt.keystore.classpath}")
    private String keyStorePath;

    @Value("${token.jwt.keystore.password}")
    private String keyStorePassword;

    @Value("${token.jwt.key.alias}")
    private String keyAlias;

    @Value("${token.jwt.key.privateKeyPassPhrase}")
    private String privateKeyPassphrase;

    @Bean
    public KeyStore keyStore(){
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.keyStorePath);
            keyStore.load(resourceAsStream, this.keyStorePassword.toCharArray());
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load keystore: {}", this.keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load keystore");
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey(KeyStore keyStore){
        try {
            Key key = keyStore.getKey(this.keyAlias, this.privateKeyPassphrase.toCharArray());
            if (key instanceof RSAPrivateKey) {
                return (RSAPrivateKey) key;
            }
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load private key from keystore: {}", this.keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load private key");
    }

    @Bean
    public RSAPublicKey rsaPublicKey(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate(this.keyAlias);
            PublicKey publicKey = certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
        } catch (KeyStoreException e) {
            log.error("Unable to load private key from keystore: {}", this.keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load RSA public key");
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }
}
