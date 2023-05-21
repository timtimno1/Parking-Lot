package com.example.parkinglot.service.exception;

public class HttpErrorException extends Exception {

    /**
     * Constructs a new {@code HttpErrorException} with the default detail message.
     */
    public HttpErrorException() {
        super();
    }

    public HttpErrorException(String message) {
        super(message);
    }

    public HttpErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpErrorException(Throwable cause) {
        super(cause);
    }
}
