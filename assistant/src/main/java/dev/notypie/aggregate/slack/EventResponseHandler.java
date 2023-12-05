package dev.notypie.aggregate.slack;


import org.springframework.http.ResponseEntity;

public interface EventResponseHandler<INPUT, OUTPUT> {

    ResponseEntity<OUTPUT> generateEventResponse(INPUT event);
}