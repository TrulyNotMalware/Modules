package dev.notypie.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

//https://gist.github.com/IMS94/588e7bee646423308c23bfbff7681c94
@Slf4j
@Profile("jwt")
@Configuration
public class JwtConfiguration {

    @Value("${jwt.token.keystore.classpath}")
    private String keyStorePath;

    @Value("${jwt.token.keystore.password}")
    private String keyStorePassword;

    @Value("${jwt.token.key.alias}")
    private String keyAlias;

    @Value("${jwt.token.key.privateKeyPassPhrase}")
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
            log.error("Unable to load public key from keystore: {}", this.keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load RSA public key");
    }

}
