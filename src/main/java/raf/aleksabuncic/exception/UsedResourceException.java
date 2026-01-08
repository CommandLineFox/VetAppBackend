package raf.aleksabuncic.exception;

import org.springframework.http.HttpStatus;

public class UsedResourceException extends CustomException {
    public UsedResourceException(String message) {
        super(message, HttpStatus.IM_USED);
    }
}
