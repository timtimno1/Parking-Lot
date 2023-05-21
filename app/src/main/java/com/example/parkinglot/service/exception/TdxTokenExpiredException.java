package com.example.parkinglot.service.exception;

public class TdxTokenExpiredException extends Exception {
    /**
     * Constructs a new {@code TdxTokenExpiredException} with no specified detail message.
     */
    public TdxTokenExpiredException() {
        super();
    }

    public TdxTokenExpiredException(String message) {
        super(message);
    }

    public TdxTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TdxTokenExpiredException(Throwable cause) {
        super(cause);
    }
}
