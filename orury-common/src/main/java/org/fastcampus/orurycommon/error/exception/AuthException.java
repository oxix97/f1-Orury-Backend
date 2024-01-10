package org.fastcampus.orurycommon.error.exception;

import lombok.Getter;
import org.fastcampus.orurycommon.error.code.ErrorCode;

@Getter
public class AuthException extends RuntimeException {
    private final int status;
    private final String message;

    public AuthException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
