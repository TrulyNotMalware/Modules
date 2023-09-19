package dev.notypie.application;

import dev.notypie.domain.Client;
import dev.notypie.dto.RegisterOAuthClient;
import dev.notypie.dto.ResponseRegisteredClient;
import dev.notypie.jpa.dao.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultOAuth2ServiceImpl implements OAuth2Service {
    private final ClientRepository clientRepository;

    public ResponseRegisteredClient registerNewClient(RegisterOAuthClient oAuthClient){
        log.info("oAuth Client : {}",oAuthClient);
        return this.clientRepository.save(Client.createDefaultClient(oAuthClient)).toResponseDto();
    }
}
