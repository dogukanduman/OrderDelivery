package com.glovoapp.backender.service;

import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.util.DistanceCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class DistanceCalculatorTest {
    @Test
    public void smokeTest() {
        Location francescMacia = new Location(41.3925603, 2.1418532);
        Location placaCatalunya = new Location(41.3870194, 2.1678584);

        // More or less 2km from Francesc Macia to Placa Catalunya
        assertEquals(2.0, DistanceCalculator.calculateDistance(francescMacia, placaCatalunya), 0.5);
    }

}