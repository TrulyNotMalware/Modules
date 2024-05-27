package dev.notypie.global.error.exceptions;

import dev.notypie.global.error.ArgumentError;
import lombok.Builder;

import java.io.Serial;
import java.util.List;

public class CommandException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 5989816954175531444L;
    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public CommandException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }

    public CommandException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = null;
    }

    public String getErrorMessage(){
        return this.errorCode.getMessage();
    }
}
