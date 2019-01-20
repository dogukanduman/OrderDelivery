package com.glovoapp.backender.suite;

import com.glovoapp.backender.dao.CourierRepositoryImpTest;
import com.glovoapp.backender.dao.OrderRepositoryImpTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CourierRepositoryImpTest.class,
        OrderRepositoryImpTest.class
})
public class JunitTestSuiteRepo {
}