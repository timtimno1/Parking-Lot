package com.example.parkinglot.utils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;

import static org.junit.Assert.*;


public class HttpRequestTest {


    @Test
    public void setHeader() {
        HttpRequest httpRequest = null;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token"));
        }
        catch (IOException e) {
            throw new RuntimeException(e + "Throw IOException");
        }
        Map<String, String> map = new HashMap<>();
        map.put("test", "HeaderData");
        httpRequest.setHeader(map);
        assertEquals(map, httpRequest.getHeaderData());
    }

    @Test
    public void setBodyData() {
        HttpRequest httpRequest = null;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token"));
        }
        catch (IOException e) {
            throw new RuntimeException(e + "Throw IOException");
        }
        Map<String, String> map = new HashMap<>();
        map.put("test", "BodyData");
        httpRequest.setBodyData(map);
        assertEquals(map, httpRequest.getBodyData());
    }

    @Test
    public void requestSuccess() {
        CountDownLatch latch = new CountDownLatch(1);
        HttpRequest httpRequest = null;
        try {
            httpRequest = new HttpRequest(new URL("https://google.com/"));
        }
        catch (IOException e) {
            throw new RuntimeException(e + "Throw IOException");
        }
        httpRequest.request((int httpCode, String response) -> {
            assertTrue(true);
            latch.countDown();
        }, (int httpCode, String errorMessage) -> {
            latch.countDown();
            fail();
        });
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void requestFail() {
        CountDownLatch latch = new CountDownLatch(1);
        HttpRequest httpRequest = null;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token"));
        }
        catch (IOException e) {
            throw new RuntimeException(e + "Throw IOException");
        }
        httpRequest.request((int httpCode, String response) -> {
            latch.countDown();
            fail();
        }, (int httpCode, String errorMessage) -> {
            latch.countDown();
            assertTrue(true);
        });
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void setRequestMethod() {
    }


}
