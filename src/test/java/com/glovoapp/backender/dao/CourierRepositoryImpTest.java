package com.glovoapp.backender.dao;

import com.glovoapp.backender.dao.imp.CourierRepositoryImp;
import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Vehicle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourierRepositoryImp.class)
public class CourierRepositoryImpTest {

    @Autowired
    public CourierRepository courierRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void findOneExisting() {
        Courier courier = courierRepository.findById("courier-1");
        Courier expected = new Courier().withId("courier-1")
                .withBox(true)
                .withName("Manolo Escobar")
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(new Location(41.3965463, 2.1963997));

        assertEquals(expected, courier);
    }

    @Test
    public void findOneNotExisting() {
        Courier courier = new CourierRepositoryImp().findById("bad-courier-id");
        assertNull(courier);
    }

    @Test
    public void findAll() {
        List<Courier> all = new CourierRepositoryImp().findAll();
        assertFalse(all.isEmpty());
    }
}