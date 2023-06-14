package com.example.parkinglot.utils;

import org.junit.Test;

import static com.example.parkinglot.utils.CityPhonePrefix.autoAddRegionNumber;
import static com.example.parkinglot.utils.CityPhonePrefix.getCityPhonePrefix;
import static org.junit.Assert.assertEquals;


public class CityPhonePrefixTest {

    @Test
    public void testGetCityPhonePrefix_ValidCities_ReturnsPrefixes() {
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
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCityPhonePrefix_UnknownCity_ThrowsException() {
        getCityPhonePrefix("UnknownCity");
    }

    @Test
    public void testAutoAddRegionNumber_Cellphone_ReturnsNumberUnchanged() {
        String cellphone = "0928098455";
        assertEquals(cellphone, autoAddRegionNumber(cellphone, null));
    }

    @Test
    public void testAutoAddRegionNumber_ValidNumberWithRegion_ReturnsNumberWithPrefix() {
        String number = "03-4989295";
        String expected = "03 - 4989295";
        assertEquals(expected, autoAddRegionNumber(number, "Taoyuan"));
    }

    @Test
    public void testAutoAddRegionNumber_ValidNumberWithoutRegion_ReturnsNumberWithPrefix() {
        String number = "4989295";
        for (Cities city : Cities.values()) {
            String expected = CityPhonePrefix.getCityPhonePrefix(city.toString()) + " - " + number;
            assertEquals(expected, autoAddRegionNumber(number, city.toString()));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAutoAddRegionNumber_InvalidNumber_ThrowsException() {
        String illegalNumber = "1234";
        autoAddRegionNumber(illegalNumber, null);
    }
}
