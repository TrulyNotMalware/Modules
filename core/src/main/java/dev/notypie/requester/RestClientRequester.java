package dev.notypie.requester;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

public class RestClientRequester {

    public static final String defaultContentType = "application/json; charset=utf-8";
    private final RestClient restClient;

    @Builder
    public RestClientRequester(String baseUrl, String authorization){
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, defaultContentType);
                    if( authorization != null && !authorization.isBlank() ) headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+authorization);
                })
                .build();
    }

    public <T> ResponseEntity<T> post(String uri, String authorizationHeader, Object body, Class<T> responseType){
        RestClient.RequestBodySpec spec =  this.restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON);
        if( authorizationHeader != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+authorizationHeader);
        return spec.body(body).retrieve().toEntity(responseType);
    }

    public <T> ResponseEntity<T> get(String uri, String authorizationHeader, Class<T> responseType){
        RestClient.RequestHeadersSpec<?> spec = this.restClient.get().uri(uri);
        if( authorizationHeader != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+authorizationHeader);
        return spec.retrieve().toEntity(responseType);
    }

    public <T> ResponseEntity<T> delete(String uri, String authorizationHeader, Class<T> responseType){
        RestClient.RequestHeadersSpec<?> spec = this.restClient.delete().uri(uri);
        if( authorizationHeader != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+authorizationHeader);
        return spec.retrieve().toEntity(responseType);
    }

    public <T> ResponseEntity<T> put(String uri, String authorizationHeader, Object body, Class<T> responseType){
        RestClient.RequestBodySpec spec =  this.restClient.put()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON);
        if( authorizationHeader != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+authorizationHeader);
        return spec.body(body).retrieve().toEntity(responseType);
    }

    public <T> ResponseEntity<T> patch(String uri, String authorizationHeader, Object body, Class<T> responseType){
        RestClient.RequestBodySpec spec =  this.restClient.patch()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON);
        if( authorizationHeader != null )
            spec.header(HttpHeaders.AUTHORIZATION, "Bearer "+authorizationHeader);
        return spec.body(body).retrieve().toEntity(responseType);
    }
}
