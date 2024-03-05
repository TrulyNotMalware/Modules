package dev.notypie.requester;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

public class ConcurrentHttpRequesterTest {

    public final int POOL_SIZE = 10;
    final String requestBaseUrl = "https://jsonplaceholder.typicode.com/comments/";
    final RestClient restClientRequester = RestClient.builder()
            .baseUrl(requestBaseUrl)
            .build();

    /**
     * This is a test class for the `ConcurrentHttpRequester` class, particularly focusing on the `submit` method.
     * The `submit` method sends a request to the server using an `RequestBodySpec`, and updates the success and failure counts.
     */
    @DisplayName("shouldIncrementSuccessfullyWhen2xxResponse")
    @Test
    public void successfullyGet2XXResponse(){
        //given
        Map<String, String> body = new HashMap<>();
        body.put("name","testName");
        body.put("email","test@test.com");
        body.put("body","testbody");
        final ConcurrentHttpRequester requester = new ConcurrentHttpRequester(POOL_SIZE);
        //when
        for(int i=0;i<POOL_SIZE/2;i++){
            requester.submit(this.restClientRequester.post().uri("").contentType(MediaType.APPLICATION_JSON).body(body));
            requester.submit(this.restClientRequester.get().uri("1"));
        }
        requester.await();
        //then
        Assertions.assertEquals(requester.getSuccessCount(), POOL_SIZE);
        Assertions.assertEquals(requester.getFailCount(), 0);
    }

    @DisplayName("shouldIncrementFailCountWhen4XXResponse")
    @Test
    public void successfullyGet4XXResponse(){
        //given
        Map<String, String> body = new HashMap<>();
        body.put("name","testName");
        body.put("email","test@test.com");
        body.put("body","testbody");
        final ConcurrentHttpRequester requester = new ConcurrentHttpRequester(POOL_SIZE);
        //when
        for(int i=0;i<POOL_SIZE/2;i++){
            requester.submit(this.restClientRequester.post().uri("134/13").contentType(MediaType.APPLICATION_JSON).body(body));
            requester.submit(this.restClientRequester.get().uri("10000"));
        }
        requester.await();
        //then
        Assertions.assertEquals(requester.getSuccessCount(), 0);
        Assertions.assertEquals(requester.getFailCount(), POOL_SIZE);
    }

//    @Test
//    public void shouldIncrementFailCountWhen4xx() throws InterruptedException {
//        // Given
//        ResponseEntity response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
//        when(requestBodySpec.retrieve()).thenReturn(response);
//
//        // When
//        ConcurrentHttpRequester concurrentHttpRequester = new ConcurrentHttpRequester(5);
//        concurrentHttpRequester.submit(requestBodySpec);
//        Thread.sleep(100);  // allow time for the async task to complete
//
//        // Then
//        assertThat(concurrentHttpRequester.getSuccessCount(), is(0));
//        assertThat(concurrentHttpRequester.getFailCount(), is(1));
//    }
}
