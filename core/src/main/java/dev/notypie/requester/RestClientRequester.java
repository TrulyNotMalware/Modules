package dev.notypie.requester;

import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.io.Serial;
import java.io.Serializable;

public class RestClientRequester implements Serializable {
    @Serial
    private static final long serialVersionUID = -6382872408981319845L;

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
