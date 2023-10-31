package dev.notypie.exceptions;

import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDomainException extends RuntimeException{
    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public UserDomainException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }
}
