package dev.notypie.application;

import dev.notypie.base.SpringMockTest;
import dev.notypie.builders.MockUserBuilders;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.jwt.dto.JwtDto;
import dev.notypie.jwt.utils.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@Tag("security")
@ActiveProfiles("jwt")
public class RefreshTokenServiceTest extends SpringMockTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private UsersRepository userRepository;

    Users user;
    Long id;
    List<String> roles;

    @BeforeEach
    void setUp(){
        this.user = this.userRepository.save(MockUserBuilders.createDefaultUsers());
        this.id = this.userRepository.findByUserIdWithException(this.user.getUserId()).getId();
        this.roles = new ArrayList<>();
        roles.add("testRole");
    }
    @Test
    @DisplayName("[mod.Security] Token generate work successfully.")
    void generateNewTokens() {
        //given
        JwtDto tokens = this.refreshTokenService.generateNewTokens(this.id, this.roles);
        //when
        String refreshToken = tokens.getRefreshToken();
        String accessToken = tokens.getAccessToken();
        //then
        Assertions.assertFalse(this.provider.isExpiredToken(accessToken));
        Assertions.assertFalse(this.provider.isExpiredToken(refreshToken));
        Assertions.assertTrue(this.provider.validateJwtToken(accessToken));
        Assertions.assertTrue(this.provider.validateJwtToken(refreshToken));
    }

    @Test
    void logoutToken() {
    }

    @Test
    @DisplayName("[mod.Security] Successfully create Tokens")
    void createRefreshToken() {
        //given

        //when
        //then
    }
}