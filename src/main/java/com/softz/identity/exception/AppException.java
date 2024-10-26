package com.softz.identity.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private String[] params;
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode, String... params) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.params = params;
    }
}
