package dev.notypie.global.error.exceptions;

import dev.notypie.global.error.ArgumentError;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class JsonConvertException extends RuntimeException{

    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public JsonConvertException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }
}