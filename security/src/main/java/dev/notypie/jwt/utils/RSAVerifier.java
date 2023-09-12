package dev.notypie.jwt.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("jwt, oauth")
public class RSAVerifier implements JwtVerifier{

    @Override
    public boolean verifySignature() {
        return false;
    }
}
