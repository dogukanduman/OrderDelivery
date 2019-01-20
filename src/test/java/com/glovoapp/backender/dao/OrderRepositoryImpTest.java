package com.glovoapp.backender.dao;

import com.glovoapp.backender.dao.imp.OrderRepositoryImp;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Order;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderRepositoryImp.class)
public class OrderRepositoryImpTest {

    @Autowired
    public OrderRepository orderRepository;

    @BeforeAll
    public void setUp() {

    }


    @Test
    public void findAll() {
        List<Order> orders = orderRepository.findAll();

        assertFalse(orders.isEmpty());

        Order firstOrder = orders.get(0);

        Order expected = new Order().withId("order-1")
                .withDescription("I want a pizza cut into very small slices")
                .withFood(true)
                .withVip(false)
                .withPickup(new Location(41.3965463, 2.1963997))
                .withDelivery(new Location(41.407834, 2.1675979));

        assertEquals(expected, firstOrder);
    }
}