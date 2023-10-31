package dev.notypie.global.error;


import dev.notypie.global.error.exceptions.CommonErrorCodeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    //Default Exception Handlers.
    /**
     * Check Method Argument is valid. Include @RequestBody binding.
     * @param e MethodArgumentNotValidException
     * @return ResponseEntity<>(ErrorResponse,HttpStatus)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException e){
        log.error("Method Not Allowed {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.builder().errorCode(CommonErrorCodeImpl.INVALID_PARAMETER).build();
        return new ResponseEntity<>(response, CommonErrorCodeImpl.INVALID_PARAMETER.getStatus());
    }

    /**
     * Check Method Argument mismatch exceptions.
     * @param e : MethodArgumentTypeMismatchException
     * @return ResponseEntity<>(ErrorResponse,HttpStatus)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgsTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("Method argument type mismatch", e);
        List<ArgumentError> argumentErrors = new ArrayList<>();
        String value = e.getValue() == null ? "" : e.getValue().toString();
        argumentErrors.add(new ArgumentError(e.getName(), value, e.getErrorCode()));
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(CommonErrorCodeImpl.INVALID_ARGUMENT_TYPE).argumentErrors(argumentErrors).build();
        return new ResponseEntity<>(errorResponse,CommonErrorCodeImpl.INVALID_ARGUMENT_TYPE.getStatus());
    }

//FIXME   class file for jakarta.servlet.ServletException not found Exception
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    protected ResponseEntity<ErrorResponse> handleRequestHttpMethodNotSupportException(HttpRequestMethodNotSupportedException e){
//        log.error("Request http methods are not allowed.", e);
//        //FIXME create detail with e.getMethod()
//        final ErrorResponse errorResponse = ErrorResponse.builder()
//                .errorCode(CommonErrorCodeImpl.METHOD_NOT_ALLOWED).build();
//        return new ResponseEntity<>(errorResponse,CommonErrorCodeImpl.METHOD_NOT_ALLOWED.getStatus());
//    }

}
