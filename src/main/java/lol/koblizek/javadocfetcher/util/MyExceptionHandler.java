package lol.koblizek.javadocfetcher.util;

import lol.koblizek.javadocfetcher.models.http.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(415).body(new ErrorResponse("Unsupported media type, please use application/json", 
                new StackTraceElement[0]));
    }
}
