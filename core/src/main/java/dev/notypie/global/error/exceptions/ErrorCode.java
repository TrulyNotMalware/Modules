package dev.notypie.global.error.exceptions;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();

    String name();
    String getMessage();
}
