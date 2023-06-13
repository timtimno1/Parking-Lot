package com.example.parkinglot.service.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class HttpErrorExceptionTest {

    @Test
    public void testDefaultConstructor() {
        HttpErrorException exception = new HttpErrorException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String message = "Test message";
        HttpErrorException exception = new HttpErrorException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Test message";
        Throwable cause = new RuntimeException("Test cause");
        HttpErrorException exception = new HttpErrorException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Test cause");
        HttpErrorException exception = new HttpErrorException(cause);
        assertEquals("java.lang.RuntimeException: Test cause", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
