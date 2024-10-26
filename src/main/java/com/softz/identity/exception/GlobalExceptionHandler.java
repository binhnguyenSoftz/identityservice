package com.softz.identity.exception;

import com.softz.identity.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(
            Exception exception) {
        log.error("Uncategorized error: ", exception);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(apiResponse);
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
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        log.error("Uncategorized error: ", exception);
        ErrorCode errorCode = ErrorCode.MISSING_MESSAGE_KEY;

        String messageKey = exception.getFieldError()
                .getDefaultMessage();

        Map<String, Object> attributes = new HashMap<>();
        try {
            errorCode = ErrorCode.valueOf(messageKey);

            var constraintViolation =
                    exception.getBindingResult()
                            .getAllErrors()
                            .getFirst()
                            .unwrap(ConstraintViolation.class);

            attributes = constraintViolation.
                    getConstraintDescriptor()
                    .getAttributes();

        } catch (IllegalArgumentException e) {
            log.error("Invalid message key: ", e);
        }

        String message = mapAttribute(errorCode.getMessage(), attributes);

        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(
            AppException exception) {
        ApiResponse apiResponse = new ApiResponse();

        ErrorCode errorCode = exception.getErrorCode();

        apiResponse.setCode(errorCode.getCode());

        String message = buildMessage(errorCode.getMessage(), exception.getParams());

        apiResponse.setMessage(message);

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    private String buildMessage(String message, String[] params) {
        return String.format(message, params);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }
}
