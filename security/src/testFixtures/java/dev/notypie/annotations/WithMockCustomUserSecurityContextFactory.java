package dev.notypie.annotations;


import dev.notypie.builders.MockUserBuilders;
import dev.notypie.domain.Users;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.Collection;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Users mockUser = MockUserBuilders.createDefaultUsers(annotation.userId(), annotation.userName(), annotation.password());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(mockUser.getRole()));
        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser, mockUser.getPassword(), authorities);
        context.setAuthentication(auth);
        return context;
    }

}
