package dev.notypie.requester;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class RestClientRequesterTest {

    final String requestBaseUrl = "https://jsonplaceholder.typicode.com/comments/";
    final String commentNumber = "1";
    final RestClientRequester requester = RestClientRequester.builder()
            .baseUrl(requestBaseUrl)
            .build();

    @BeforeEach
    public void setUp(){
        final String requestBaseUrl = "https://jsonplaceholder.typicode.com/comments/";
    }

    @DisplayName("Successfully handling get request")
    @Test
    public void getRequest(){
        //given
        //when
        ResponseEntity<Object> response = this.requester.get(commentNumber, null, Object.class);
        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @DisplayName("Successfully handling post request")
    @Test
    public void postRequest(){
        //given
        Map<String, String> body = new HashMap<>();
        body.put("name","testName");
        body.put("email","test@test.com");
        body.put("body","testbody");
        //when
        ResponseEntity<Object> response = this.requester.post("", null, body, Object.class);
        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @DisplayName("Successfully handling patch request")
    @Test
    public void patchRequest(){
        //given
        Map<String, String> body = new HashMap<>();
        body.put("name","testName");
        body.put("email","test@test.com");
        body.put("body","testbody");
        final String id = "1";
        //when
        ResponseEntity<?> response = this.requester.patch(id,null,body,Object.class);
        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @DisplayName("Successfully handling delete request")
    @Test
    public void deleteRequest(){
        //given
        String id = "1";
        //when
        ResponseEntity<?> response = this.requester.delete(id,null, Object.class);
        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @DisplayName("Successfully handling put request")
    @Test
    public void putRequest(){
        //given
        Map<String, String> body = new HashMap<>();
        body.put("name","testName");
        body.put("email","test@test.com");
        body.put("body","testbody");
        final String id = "1";
        //when
        ResponseEntity<?> response = this.requester.put(id,null,body,Object.class);
        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
