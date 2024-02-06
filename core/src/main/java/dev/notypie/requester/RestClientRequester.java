package dev.notypie.requester;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientRequester {

    @Value("${core.requester.config.baseUrl}")
    private String baseUrl;

    @Value("${core.requester.config.authorization}")
    private String authorization;

    public static final String defaultContentType = "application/json; charset=utf-8";
    private final RestClient restClient = RestClient.builder()
            .baseUrl(this.baseUrl)
            .defaultHeaders(headers -> {
                headers.add(HttpHeaders.CONTENT_TYPE, defaultContentType);
                if( authorization != null ) headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+this.authorization);
            })
            .build();

    public <T> ResponseEntity<T> post(String uri, String authorizationHeader, Object body, Class<T> responseType){
        RestClient.RequestBodySpec spec =  this.restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON);
        if( authorizationHeader != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+authorizationHeader);
        else if( this.authorization != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+this.authorization);
        return spec.body(body).retrieve().toEntity(responseType);
    }
}
