package com.example.parkinglot.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TdxModelTest {
    private TdxModel tdxModel;
    @Before
    public void setUp() throws Exception {
        tdxModel = new TdxModel();
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetCityPhonePrefix() {
        // Test known cities
        assertEquals("02", tdxModel.getCityPhonePrefix("Taipei"));
        assertEquals("02", tdxModel.getCityPhonePrefix("Keelung"));
        assertEquals("03", tdxModel.getCityPhonePrefix("Taoyuan"));
        assertEquals("03", tdxModel.getCityPhonePrefix("Hsinchu"));
        assertEquals("03", tdxModel.getCityPhonePrefix("HualienCounty"));
        assertEquals("03", tdxModel.getCityPhonePrefix("YilanCounty"));
        assertEquals("037", tdxModel.getCityPhonePrefix("MiaoliCounty"));
        assertEquals("04", tdxModel.getCityPhonePrefix("Taichung"));
        assertEquals("049", tdxModel.getCityPhonePrefix("NantouCounty"));
        assertEquals("05", tdxModel.getCityPhonePrefix("Chiayi"));
        assertEquals("05", tdxModel.getCityPhonePrefix("ChiayiCounty"));
        assertEquals("06", tdxModel.getCityPhonePrefix("Tainan"));
        assertEquals("07", tdxModel.getCityPhonePrefix("Kaohsiung"));
        assertEquals("08", tdxModel.getCityPhonePrefix("PingtungCounty"));
        assertEquals("089", tdxModel.getCityPhonePrefix("TaitungCounty"));
        assertEquals("082", tdxModel.getCityPhonePrefix("KinmenCounty"));
        assertEquals("0836", tdxModel.getCityPhonePrefix("LianjiangCounty"));
        // Test unknown city
        try {
            tdxModel.getCityPhonePrefix("UnknownCity");
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}