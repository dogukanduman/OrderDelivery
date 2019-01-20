package com.glovoapp.backender.service;

import com.glovoapp.backender.dao.CourierRepository;
import com.glovoapp.backender.dao.OrderRepository;
import com.glovoapp.backender.dao.imp.CourierRepositoryImp;
import com.glovoapp.backender.dao.imp.OrderRepositoryImp;
import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.domain.Vehicle;
import com.glovoapp.backender.dto.OrderVM;
import com.glovoapp.backender.dto.SlotVM;
import com.glovoapp.backender.service.imp.CourierServiceImp;
import com.glovoapp.backender.service.imp.OrderServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderRepositoryImp.class, CourierRepositoryImp.class, OrderServiceImp.class, CourierServiceImp.class})
public class OrderServiceImpTest {


    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public CourierRepository courierRepository;

    @Autowired
    public OrderService orderService;

    @Before
    public void setUp() {

        orderRepository.deleteAll();
        courierRepository.deleteAll();


        Order pizza = new Order().withId("order-1")
                .withDescription("pizza")
                .withFood(true)
                .withVip(false)
                .withPickup(new Location(41.40440729233118, 2.1780786691277085))
                .withDelivery(new Location(41.407834, 2.1675979));
        orderRepository.add(pizza);

        Order flamingo = new Order().withId("order-2")
                .withDescription("flamingo")
                .withFood(true)
                .withVip(false)
                .withPickup(new Location(41.38790681247524, 2.1661962085744095))
                .withDelivery(new Location(41.407834, 2.1675979));
        orderRepository.add(flamingo);


        Order cake = new Order().withId("order-3")
                .withDescription("cake")
                .withFood(true)
                .withVip(false)
                .withPickup(new Location(41.40504741338881, 2.1727005988333596))
                .withDelivery(new Location(41.407834, 2.1675979));
        orderRepository.add(cake);

        Order soFar = new Order().withId("order-4")
                .withDescription("slave")
                .withFood(true)
                .withVip(false)
                .withPickup(new Location(0.0, 0.0))
                .withDelivery(new Location(0.0, 0.0));
        orderRepository.add(soFar);


        Order vip = new Order().withId("order-5")
                .withDescription("vip")
                .withFood(true)
                .withVip(true)
                .withPickup(new Location(41.393457055153675, 2.1873072382450744))
                .withDelivery(new Location(41.393457055153675, 2.1873072382450744));
        orderRepository.add(vip);

        Order other = new Order().withId("order-6")
                .withDescription("other")
                .withFood(false)
                .withVip(false)
                .withPickup(new Location(41.40732297416662, 2.1764821793089455))
                .withDelivery(new Location(41.393457055153675, 2.1873072382450744));
        orderRepository.add(other);


        Courier courier1 = new Courier().withId("courier-box-motor")
                .withBox(true)
                .withName("Manolo Escobar")
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(new Location(41.3965463, 2.1963997));
        courierRepository.add(courier1);

        Courier courier2 = new Courier().withId("courier-non-box-motor")
                .withBox(false)
                .withName("Vicki Hume")
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(new Location(41.405536955908154, 2.1963997));
        courierRepository.add(courier2);

        Courier courier3 = new Courier().withId("courier-box-bicycle")
                .withBox(true)
                .withName("Vicki Hume")
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(new Location(41.405536955908154, 2.1963997));
        courierRepository.add(courier3);

        Courier courier4 = new Courier().withId("courier-non-box-bicycle")
                .withBox(false)
                .withName("Vicki Hume")
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(new Location(41.405536955908154, 2.1963997));
        courierRepository.add(courier4);

    }

    @Test
    public void findAll() {
        List<OrderVM> orderList = orderService.findAll();
        assertEquals(orderList.size(), 6);
    }

    @Test
    public void findOrdersWithCourierBoxMotor() {
        /**
         * There are 6 orders and courier with box and motor can take all orders
         */
        List<SlotVM> slotVMS = orderService.findOrdersByCourierId("courier-box-motor");


        int size = slotVMS.stream().map(SlotVM::getOrderDistances)
                .mapToInt(List::size)
                .sum();

        assertEquals(size, 6);
    }

    @Test
    public void findOrdersWithCourierNonBoxMotor() {
        /**
         * There are 3 orders which a courier can carry without box
         * If a courier doesn't have box can not carry a order
         * which has specific words in its description
         * such as flamingo, pizza, cake
         */
        List<SlotVM> slotVMS = orderService.findOrdersByCourierId("courier-non-box-motor");


        int size = slotVMS.stream().map(SlotVM::getOrderDistances)
                .mapToInt(List::size)
                .sum();

        assertEquals(size, 3);
    }


    @Test
    public void findOrdersWithCourierBoxBicycle() {

        /**
         * There are 5 orders which a courier can carry with box and Bicycle
         * If a courier is too far from a order for example more than 5 km
         * , order will be filtered.
         */

        List<SlotVM> slotVMS = orderService.findOrdersByCourierId("courier-box-bicycle");

        int size = slotVMS.stream().map(SlotVM::getOrderDistances)
                .mapToInt(List::size)
                .sum();

        assertEquals(size, 5);
    }

    @Test
    public void findOrdersWithCourierNonBoxBicycle() {


        /**
         * There are 5 orders which a courier can carry with Bicycle
         * But without box it can carry only 2 of them.
         */

        List<SlotVM> slotVMS = orderService.findOrdersByCourierId("courier-non-box-bicycle");

        int size = slotVMS.stream().map(SlotVM::getOrderDistances)
                .mapToInt(List::size)
                .sum();

        assertEquals(size, 2);
    }


}




