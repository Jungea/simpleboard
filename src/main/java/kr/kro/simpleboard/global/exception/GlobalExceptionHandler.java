package kr.kro.simpleboard.global.exception;

import kr.kro.simpleboard.post.exception.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException ex) {
        return ResponseEntity
                .status(ErrorCode.POST_NOT_FOUND.getStatus())
                .body(ErrorResponse.of(ErrorCode.POST_NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getStatus())
                .body(ErrorResponse.of(ErrorCode.VALIDATION_ERROR, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
