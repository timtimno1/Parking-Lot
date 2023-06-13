package com.example.parkinglot.service.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class TdxTokenExpiredExceptionTest {

    @Test
    public void testDefaultConstructor() {
        TdxTokenExpiredException exception = new TdxTokenExpiredException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String message = "Test message";
        TdxTokenExpiredException exception = new TdxTokenExpiredException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Test message";
        Throwable cause = new RuntimeException("Test cause");
        TdxTokenExpiredException exception = new TdxTokenExpiredException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Test cause");
        TdxTokenExpiredException exception = new TdxTokenExpiredException(cause);
        assertEquals("java.lang.RuntimeException: Test cause", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}
