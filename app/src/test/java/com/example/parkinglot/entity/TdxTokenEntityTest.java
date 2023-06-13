package com.example.parkinglot.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TdxTokenEntityTest {

    @Test
    public void testSetTdxToken() {
        TdxTokenEntity tdxTokenEntity = new TdxTokenEntity();
        String expectedToken = "sampleToken";
        tdxTokenEntity.setTdxToken(expectedToken);

        assertEquals(expectedToken, tdxTokenEntity.tdxToken);
    }
}
