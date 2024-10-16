package com.softz.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public class AppException extends RuntimeException {
    private final Object[] params;
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode, Object... params) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.params = params;
    }
}