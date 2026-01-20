package raf.aleksabuncic.exception;

import org.springframework.http.HttpStatus;

public class BadUsernameOrPasswordException extends CustomException {
    public BadUsernameOrPasswordException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
