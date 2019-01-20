package com.glovoapp.backender.suite;

import com.glovoapp.backender.service.CourierServiceImpTest;
import com.glovoapp.backender.service.DistanceCalculatorTest;
import com.glovoapp.backender.service.OrderServiceImpTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CourierServiceImpTest.class,
        OrderServiceImpTest.class,
        DistanceCalculatorTest.class
})
public class JunitTestSuiteService {
}