package com.softz.identity.exception;

import com.softz.identity.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleException(Exception exception) {
        ErrorCode errCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        log.error("Uncategorized error", exception);
        return ResponseEntity.status(errCode.getStatusCode()).body(
                ApiResponse.builder().code(errCode.getCode()).message(errCode.getMessage()).build()
        );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(AppException exception) {
        ErrorCode errCode = exception.getErrorCode();
        return ResponseEntity.status(errCode.getStatusCode()).body(
            ApiResponse.builder().code(errCode.getCode()).message(errCode.getMessage()).build()
        );
    }
}
