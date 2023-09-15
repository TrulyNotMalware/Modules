package dev.notypie.application;

import dev.notypie.common.utils.OAuthAttribute;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository repository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User user = delegate.loadUser(userRequest);
        //OAuth Service Name.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth Login key
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
//        FIXME OAuth User Mapper class.
        Users users = this.repository.saveOrUpdateByUserId(
                OAuthAttribute.extract(registrationId, user.getAttributes())
        );
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("oauth-user")),
                user.getAttributes(), userNameAttributeName);
    }
}
