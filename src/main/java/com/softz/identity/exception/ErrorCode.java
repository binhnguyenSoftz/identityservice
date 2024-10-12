package com.softz.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(99999, "Uncategorized error",
            HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(409000, "User not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(409100, "User existed", HttpStatus.CONFLICT);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
