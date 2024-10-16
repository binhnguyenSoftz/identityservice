package com.softz.identity.exception;

import com.softz.identity.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleException(Exception exception) {
        ErrorCode errCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        log.error("Uncategorized error", exception);
        return ResponseEntity.status(errCode.getStatusCode()).body(
                ApiResponse.builder().code(errCode.getCode()).message(errCode.getMessage()).build()
        );
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse> handlingHttpMessageNotReadableException(
        HttpMessageNotReadableException exception) {
        log.error("Uncategorized error: ", exception);
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
            .body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String messageKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.MISSING_MESSAGE_KEY;
        Map attributes = new HashMap<>();
        try {
            errorCode = ErrorCode.valueOf(messageKey);

            var constraintViolation =
                exception.getBindingResult()
                    .getAllErrors()
                    .getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolation
                .getConstraintDescriptor()
                .getAttributes();

        } catch (IllegalArgumentException e) {
            log.error("Invalid message key: ", e);
        }

        String message = mapAttribute(errorCode.getMessage(), attributes);

        return ResponseEntity
            .status(errorCode.getStatusCode())
            .body(
                ApiResponse
                    .builder()
                    .code(errorCode.getCode())
                    .message(message)
                    .build()
            );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(AppException exception) {
        ErrorCode errCode = exception.getErrorCode();
        String message = buildMessage(errCode.getMessage(), exception.getParams());
        return ResponseEntity
            .status(errCode.getStatusCode())
            .body(
                ApiResponse
                    .builder()
                    .code(errCode.getCode())
                    .message(message)
                    .build()
        );
    }

    private String buildMessage(String message, Object ...params) {
        return MessageFormat.format(message, params);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
            .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }
}
