package dev.notypie.filters;

import dev.notypie.jwt.utils.JwtTokenProvider;
import dev.notypie.jwt.utils.JwtVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Profile("jwt")
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtTokenProvider provider;
    private final JwtVerifier verifier;

    //Constructor Injection.
    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider provider, JwtVerifier verifier) {
        super(Config.class);
        this.provider = provider;
        this.verifier = verifier;
    }

    @Override
    public GatewayFilter apply(Config config){
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){ // Http Header 에 AUTHORIZATION 이 없으면
                return onError(exchange, "Header Authorization not found.", HttpStatus.UNAUTHORIZED); // FIXME UnAuthorization 말고, Login페이지로 Redirect 필요.
            }
            String headers = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            log.debug("headers : {}",headers);
            //Jwt Token 판별.
            String token = headers.replace("Bearer ","");
            log.info(token);
            if(this.provider.validateJwtToken(token)){
                Map<String, Object> userParseInfo = this.verifier.userParser(this.provider.getClaimsFromJwtToken(token));
                return chain.filter(exchange.mutate().request(
                        request.mutate().header("X-AUTH-TOKEN", token)//Original Headers.
                                .header("userRole", userParseInfo.get("roles").toString())
                                .header("userId", (String) userParseInfo.get("userId")).build()
                ).build());
            }else{
                return onError(exchange, "Not a Valid Token", HttpStatus.UNAUTHORIZED);
            }
        });
    }
    private Mono<Void> onError(ServerWebExchange exchange, String e, HttpStatus status){
        log.error(e);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {

    }
}