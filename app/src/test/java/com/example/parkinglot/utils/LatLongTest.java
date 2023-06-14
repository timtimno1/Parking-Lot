package com.example.parkinglot.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.android.gms.maps.model.LatLng;

public class LatLongTest {
    private LatLong latLong;

    @Before
    public void setUp() {
        latLong = new LatLong(25.048630, 121.519015);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getLat() {
        assertEquals(25.048630, latLong.getLat(), 0.001);
    }

    @Test
    public void getLon() {
        assertEquals(121.519015, latLong.getLon(), 0.001);
    }

    @Test
    public void add() {
        LatLong temp = latLong.add(1.0, 2.0);
        assertEquals(26.048630, temp.getLat(), 0.001);
        assertEquals(123.519015, temp.getLon(), 0.001);
    }

    @Test
    public void testToString() {
        assertEquals("LatLong [lat=25.04863, lon=121.519015]", latLong.toString());
    }
    @Test
    public void testConvertToLatLng() {
        double lat = 37.7749;
        double lon = -122.4194;
        LatLong instance = new LatLong(lat, lon);

        LatLng expectedLatLng = new LatLng(lat, lon);
        LatLng actualLatLng = instance.convertToLatLng();

        Assert.assertEquals(expectedLatLng, actualLatLng);
    }
}
