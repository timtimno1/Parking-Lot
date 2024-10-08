package com.example.parkinglot.models;

import static com.example.parkinglot.utils.CityPhonePrefix.autoAddRegionNumber;
import static com.example.parkinglot.utils.CityPhonePrefix.getCityPhonePrefix;
import static org.junit.Assert.*;

import com.example.parkinglot.utils.Cities;
import com.example.parkinglot.utils.CityPhonePrefix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TdxModelTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetCityPhonePrefix() {
        // Test known cities
        assertEquals("02", getCityPhonePrefix("Taipei"));
        assertEquals("02", getCityPhonePrefix("Keelung"));
        assertEquals("03", getCityPhonePrefix("Taoyuan"));
        assertEquals("03", getCityPhonePrefix("Hsinchu"));
        assertEquals("03", getCityPhonePrefix("HualienCounty"));
        assertEquals("03", getCityPhonePrefix("YilanCounty"));
        assertEquals("037", getCityPhonePrefix("MiaoliCounty"));
        assertEquals("04", getCityPhonePrefix("Taichung"));
        assertEquals("049", getCityPhonePrefix("NantouCounty"));
        assertEquals("05", getCityPhonePrefix("Chiayi"));
        assertEquals("05", getCityPhonePrefix("ChiayiCounty"));
        assertEquals("06", getCityPhonePrefix("Tainan"));
        assertEquals("07", getCityPhonePrefix("Kaohsiung"));
        assertEquals("08", getCityPhonePrefix("PingtungCounty"));
        assertEquals("089", getCityPhonePrefix("TaitungCounty"));
        assertEquals("082", getCityPhonePrefix("KinmenCounty"));
        assertEquals("0836", getCityPhonePrefix("LienchiangCounty"));
        // Test unknown city
        try {
            getCityPhonePrefix("UnknownCity");
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testAutoAddRegionNumber() {
        String cellphone = "0928098455";
        String legalNumberWithRegion1 = "03-4989295";
        String legalNumberWithRegion2 = "024989295";
        String legalNumberWithoutRegion1 = "4989295";
        String legalNumberWithoutRegion2 = "12345678";
        assertEquals("0928098455", autoAddRegionNumber(cellphone, null));
        assertEquals("03 - 4989295", autoAddRegionNumber(legalNumberWithRegion1, "Taoyuan"));
        assertEquals("02 - 4989295", autoAddRegionNumber(legalNumberWithRegion2, "Taipei"));
        assertEquals("03 - 4989295", autoAddRegionNumber(legalNumberWithoutRegion1, "Taoyuan"));
        assertEquals("03 - 12345678", autoAddRegionNumber(legalNumberWithoutRegion2, "Taoyuan"));
        for(Cities city : Cities.values()) {
            assertEquals(CityPhonePrefix.getCityPhonePrefix(city.toString()) + " - 4989295", autoAddRegionNumber(legalNumberWithoutRegion1, city.toString()));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAutoAddRegionNumberThrowException() {
        String illegalNumber = "1234";
        autoAddRegionNumber(illegalNumber, null);
    }
}