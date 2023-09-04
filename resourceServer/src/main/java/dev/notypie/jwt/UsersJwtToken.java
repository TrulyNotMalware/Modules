package dev.notypie.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
public class UsersJwtToken extends JwtAuthenticationToken {
    private final Long userId;

    public UsersJwtToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);
        this.userId = Long.valueOf(jwt.getClaimAsString("userId"));
    }
}
