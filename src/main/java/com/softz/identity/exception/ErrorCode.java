package com.softz.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(99999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT(9000, "Invalid input", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(409000, "User not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(409100, "User existed", HttpStatus.CONFLICT),
    USER_ID_NOT_FOUND(409101, "User id {0} not found", HttpStatus.BAD_REQUEST),

    MISSING_MESSAGE_KEY(100101, "Invalid message", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(100100, "Username's length must be betweens {min} and {max}", HttpStatus.BAD_REQUEST),
    INVALID_DATE_OF_BIRTH(100102, "User's age must be equal or greater than {min}", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
