package dev.notypie.aggregate.slack;


import org.springframework.http.ResponseEntity;

public interface EventHandler<INPUT, OUTPUT> {

    ResponseEntity<OUTPUT> generateEventResponse(INPUT event);
}