package com.glovoapp.backender.service;

import com.glovoapp.backender.dao.CourierRepository;
import com.glovoapp.backender.dao.imp.CourierRepositoryImp;
import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Vehicle;
import com.glovoapp.backender.exception.ResourceNotFoundException;
import com.glovoapp.backender.service.imp.CourierServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CourierRepositoryImp.class, CourierServiceImp.class})
public class CourierServiceImpTest {

    @Autowired
    public CourierRepository courierRepository;

    @Autowired
    public CourierService courierService;


    @Before
    public void setUp() {

        courierRepository.deleteAll();

        Courier courier1 = new Courier().withId("courier-1")
                .withBox(true)
                .withName("Manolo Escobar")
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(new Location(41.3965463, 2.1963997));

        Courier courier2 = new Courier().withId("courier-2")
                .withBox(true)
                .withName("Vicki Hume")
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(new Location(41.405536955908154, 42.1811978376726726));


        courierRepository.add(courier1);
        courierRepository.add(courier2);
    }

    @Test
    public void findByIdTest() {

        Courier courier = courierService.findById("courier-2");

        assertEquals(courier.getId(), "courier-2");

    }

    @Test
    public void ResourceNotFoundExceptionTest() {
        assertThrows(ResourceNotFoundException.class, () -> {
            courierService.findById("bad-courier-id");
        });

    }

    @Test
    public void findAllTest() {

        List<Courier> courierList = courierService.findAll();

        assertEquals(courierList.size(), 2);
    }
}