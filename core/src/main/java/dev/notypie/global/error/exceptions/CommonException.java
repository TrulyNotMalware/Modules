package dev.notypie.global.error.exceptions;

import dev.notypie.global.error.ArgumentError;
import lombok.Builder;

import java.io.Serial;
import java.util.List;

public class CommonException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -2135547636649720369L;

    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public CommonException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }
}
