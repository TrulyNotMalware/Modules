package dev.notypie.authentication;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;

//https://github.com/Hong1008/blog-tutorials/blob/master/spring-oauth-jwt/src/main/java/com/example/demo/authentication/SimpleJwtAuthenticationConverter.java
public abstract class SimpleJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(source);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        return convert(source, authorities);
    }

    public abstract AbstractAuthenticationToken convert(Jwt jwt, Collection<GrantedAuthority> authorities);
}
