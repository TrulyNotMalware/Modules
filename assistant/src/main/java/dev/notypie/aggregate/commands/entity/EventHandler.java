package dev.notypie.aggregate.commands.entity;


import org.springframework.http.ResponseEntity;

public interface EventHandler<INPUT, OUTPUT> {

    ResponseEntity<OUTPUT> generateEventResponse(INPUT event);
}