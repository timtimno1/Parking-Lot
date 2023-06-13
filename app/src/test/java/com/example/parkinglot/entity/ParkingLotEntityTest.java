package com.example.parkinglot.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.example.parkinglot.dto.ParkingLotInfoDto;


public class ParkingLotEntityTest {
    private ParkingLotEntity parkingLotEntity;

    @Before
    public void setUp() {
        parkingLotEntity = new ParkingLotEntity();
        parkingLotEntity.parkingLotName = "Parking Lot 1";
        parkingLotEntity.city = "Taipei";
        parkingLotEntity.address = "台北市大安區忠孝東路三段1號";
        parkingLotEntity.phoneNumber = " 02-2771-2171";
        parkingLotEntity.fareDescription = "國立臺北科技大學，簡稱臺北科大、北科大、北科，是一所位於臺北市大安區的國立研究型科技大學，前身為臺北工專，為昔日三大工專之一";
        parkingLotEntity.isFavorite = false;
    }

    @Test
    public void testToParkingLotInfoDto() {
        ParkingLotInfoDto parkingLotInfoDto = ParkingLotEntity.toParkingLotInfoDto(parkingLotEntity);

        assertEquals(parkingLotEntity.parkingLotName, parkingLotInfoDto.getParkingLotName());
        assertEquals(parkingLotEntity.city, parkingLotInfoDto.getCity());
        assertEquals(parkingLotEntity.address, parkingLotInfoDto.getAddress());
        assertEquals(parkingLotEntity.phoneNumber, parkingLotInfoDto.getPhoneNumber());
        assertEquals(parkingLotEntity.fareDescription, parkingLotInfoDto.getPrice());
        assertEquals("24 hours", parkingLotInfoDto.getOpeningHours());
        assertFalse(parkingLotInfoDto.getIsFavorite());
    }

    @Test
    public void testGetCarParkID() {
        parkingLotEntity.carParkID = "ABC123";

        assertEquals("ABC123", parkingLotEntity.getCarParkID());
    }
}
