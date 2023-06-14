package com.example.parkinglot.models;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.parkinglot.ParkingLotDataBase;
import androidx.test.core.app.ApplicationProvider;
import com.example.parkinglot.models.dao.ParkingLotDao;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.views.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MapModelTest {

    private ParkingLotDao parkingLotDao;

    @Before
    public void setUp() {
        MainActivity.setApplicationContextInstance(ApplicationProvider.getApplicationContext());
        parkingLotDao = ParkingLotDataBase.getInstance().parkingLotDao();
        ParkingLotEntity parkingLotEntity = new ParkingLotEntity();
        parkingLotEntity.carParkID = "testID";
        parkingLotEntity.parkingLotName = "test";
        parkingLotEntity.address = "testAddress";
        parkingLotEntity.fareDescription = "testFare";
        parkingLotEntity.numberOfParkingSpace = 100;
        parkingLotEntity.remainingParkingSpace = 50;
        parkingLotEntity.latitude = 120.0;
        parkingLotEntity.longitude = 120.0;
        parkingLotEntity.phoneNumber = "0912345678";
        parkingLotDao.insertAll(parkingLotEntity);
    }

    @After
    public void tearDown() {
        ParkingLotEntity parkingLotEntity = parkingLotDao.selectFromName("test");
        parkingLotDao.delete(parkingLotEntity);
    }

    @Test
    public void getParkingLotData() {
        CountDownLatch latch = new CountDownLatch(1);
        MapModel mapModel = new MapModel();
        mapModel.getParkingLotData((parkingLotEntities) -> {
            int parkingLotEntitiesSize = parkingLotEntities.size();
            assertEquals("testID", parkingLotEntities.get(parkingLotEntitiesSize - 1).carParkID);
            assertEquals("test", parkingLotEntities.get(parkingLotEntitiesSize - 1).parkingLotName);
            assertEquals("testAddress", parkingLotEntities.get(parkingLotEntitiesSize - 1).address);
            assertEquals("testFare", parkingLotEntities.get(parkingLotEntitiesSize - 1).fareDescription);
            assertEquals(100, parkingLotEntities.get(parkingLotEntitiesSize - 1).numberOfParkingSpace);
            assertEquals(50, parkingLotEntities.get(parkingLotEntitiesSize - 1).remainingParkingSpace);
            assertEquals(120.0, parkingLotEntities.get(parkingLotEntitiesSize - 1).latitude, 0.001);
            assertEquals(120.0, parkingLotEntities.get(parkingLotEntitiesSize - 1).longitude, 0.001);
            assertEquals("0912345678", parkingLotEntities.get(parkingLotEntitiesSize - 1).phoneNumber);
            latch.countDown();
        });

        try {
            latch.await();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
