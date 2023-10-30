package dev.notypie.application;

import dev.notypie.annotations.WithMockCustomUser;
import dev.notypie.base.SpringMockTest;
import dev.notypie.builders.MockUserBuilders;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.jwt.dto.JwtDto;
import dev.notypie.jwt.utils.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Tag("security")
@ActiveProfiles("jwt")
@TestPropertySource(properties = {"spring.config.location = classpath:application-expired-test.yaml"})
public class ExpiredRefreshTokenServiceTest extends SpringMockTest {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private InMemoryUserDetailsManager service;
    Users user;
    Long id;
    List<String> roles;

    @BeforeEach
    void setUp(){
        this.user = this.userRepository.save(MockUserBuilders.createDefaultUsers());
        this.id = this.userRepository.findByUserIdWithException(this.user.getUserId()).getId();
        this.roles = new ArrayList<>();
        roles.add("testRole");

        //Test with In Memory User Details
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.user.getRole()));
        this.service.createUser(new User(this.user.getUserId(), this.user.getPassword(),authorities));
    }

    @Test
//    @WithMockCustomUser(userId = MockUserBuilders.userId, userName = MockUserBuilders.userName)
    @DisplayName("[mod.Security] Reissue accessToken works successfully")
    void refreshJwtToken() {
        //given
        JwtDto newToken = this.refreshTokenService.generateNewTokens(id, roles);
        this.refreshTokenService.updateRefreshToken(id, newToken.getRefreshToken()); //Save refreshToken.
        //when
        String accessToken = newToken.getAccessToken();
        JwtDto reissueToken = this.refreshTokenService.refreshJwtToken(accessToken, newToken.getRefreshToken());
        //then
        Assertions.assertTrue(this.provider.isExpiredToken(accessToken));
        Assertions.assertNotNull(reissueToken.getAccessToken());
        Assertions.assertEquals(reissueToken.getRefreshToken(), newToken.getRefreshToken());
        Assertions.assertNotEquals(accessToken, reissueToken.getAccessToken());
    }
}
