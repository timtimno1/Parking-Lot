package com.example.parkinglot.viewmodels.DTO;
import org.junit.Assert;
import org.junit.Test;

public class ParkingAvailabilityDTOTest {

    @Test
    public void testGetCarParkID() {
        String expectedCarParkID = "CP001";
        ParkingAvailabilityDTO dto = new ParkingAvailabilityDTO(expectedCarParkID, "Parking 1", "Available");
        String actualCarParkID = dto.getCarParkID();
        Assert.assertEquals(expectedCarParkID, actualCarParkID);
    }

    @Test
    public void testSetCarParkID() {
        String expectedCarParkID = "CP002";
        ParkingAvailabilityDTO dto = new ParkingAvailabilityDTO("CP001", "Parking 1", "Available");
        dto.setCarParkID(expectedCarParkID);
        String actualCarParkID = dto.getCarParkID();
        Assert.assertEquals(expectedCarParkID, actualCarParkID);
    }

    @Test
    public void testGetCarParkName() {
        String expectedCarParkName = "Parking 1";
        ParkingAvailabilityDTO dto = new ParkingAvailabilityDTO("CP001", expectedCarParkName, "Available");
        String actualCarParkName = dto.getCarParkName();
        Assert.assertEquals(expectedCarParkName, actualCarParkName);
    }

    @Test
    public void testSetCarParkName() {
        String expectedCarParkName = "Parking 2";
        ParkingAvailabilityDTO dto = new ParkingAvailabilityDTO("CP001", "Parking 1", "Available");
        dto.setCarParkName(expectedCarParkName);
        String actualCarParkName = dto.getCarParkName();
        Assert.assertEquals(expectedCarParkName, actualCarParkName);
    }

    @Test
    public void testGetAvailability() {
        String expectedAvailability = "Available";
        ParkingAvailabilityDTO dto = new ParkingAvailabilityDTO("CP001", "Parking 1", expectedAvailability);
        String actualAvailability = dto.getAvailability();
        Assert.assertEquals(expectedAvailability, actualAvailability);
    }

    @Test
    public void testSetAvailability() {
        String expectedAvailability = "Full";
        ParkingAvailabilityDTO dto = new ParkingAvailabilityDTO("CP001", "Parking 1", "Available");
        dto.setAvailability(expectedAvailability);
        String actualAvailability = dto.getAvailability();
        Assert.assertEquals(expectedAvailability, actualAvailability);
    }
}
