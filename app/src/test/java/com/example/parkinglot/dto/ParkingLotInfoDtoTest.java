package com.example.parkinglot.dto;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotInfoDtoTest {
    private ParkingLotInfoDto parkingLotInfoDto;

    @Before
    public void setUp() {
        parkingLotInfoDto = new ParkingLotInfoDto("Parking Lot 1", "Taipei");
    }

    @Test
    public void testGetParkingLotName() {
        String expected = "Parking Lot 1";
        String actual = parkingLotInfoDto.getParkingLotName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetParkingLotName() {
        String expected = "New Parking Lot";
        parkingLotInfoDto.setParkingLotName(expected);
        String actual = parkingLotInfoDto.getParkingLotName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetCity() {
        String expected = "Taipei";
        String actual = parkingLotInfoDto.getCity();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetCity() {
        String expected = "Taoyuan";
        parkingLotInfoDto.setCity(expected);
        String actual = parkingLotInfoDto.getCity();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAddress() {
        String expected = null;
        String actual = parkingLotInfoDto.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetAddress() {
        String expected = "台北市大安區忠孝東路三段1號";
        parkingLotInfoDto.setAddress(expected);
        String actual = parkingLotInfoDto.getAddress();
        Assert.assertEquals(expected, actual);
    }

    // Add more tests for other properties and behaviors

    @Test
    public void testIsFavorite() {
        boolean expected = false;
        boolean actual = parkingLotInfoDto.getIsFavorite();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetIsFavorite() {
        boolean expected = true;
        parkingLotInfoDto.setIsFavorite(expected);
        boolean actual = parkingLotInfoDto.getIsFavorite();
        Assert.assertEquals(expected, actual);
    }
}
