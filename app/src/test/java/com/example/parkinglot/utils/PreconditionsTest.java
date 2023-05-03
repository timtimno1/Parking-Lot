package com.example.parkinglot.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PreconditionsTest {
    private Preconditions preconditions;
    boolean expressionTrue;
    Object errorMessageTrue;
    boolean expressionFalse;
    Object errorMessageFalse;
    String referenceNotNull;
    Object errorMessageNotNull;
    String referenceNull;
    Object errorMessageNull;
    @Before
    public void setUp() throws Exception {
        expressionTrue = true;
        errorMessageTrue = "This should not be thrown";
        expressionFalse = false;
        errorMessageFalse = "This should be thrown";
        referenceNotNull = "not null";
        errorMessageNotNull = "This should not be thrown";
        referenceNull = null;
        errorMessageNull = "This should be thrown";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkArgumentTest() {
        // Test when expression is true
        preconditions.checkArgument(expressionTrue, errorMessageTrue); // No exception should be thrown
        // Test when expression is false
        try {
            preconditions.checkArgument(expressionFalse, errorMessageFalse);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(errorMessageFalse, e.getMessage());
        }
    }
    @Test
    public void checkNotNullTest() {
        // Test when reference is not null
        assertEquals(referenceNotNull, preconditions.checkNotNull(referenceNotNull, errorMessageNotNull));
        // Test when reference is null
        try {
            preconditions.checkNotNull(referenceNull, errorMessageNull);
            fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            assertEquals(errorMessageNull, e.getMessage());
        }
    }
}