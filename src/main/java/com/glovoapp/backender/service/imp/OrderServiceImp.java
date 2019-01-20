package com.glovoapp.backender.service.imp;

import com.glovoapp.backender.dao.OrderRepository;
import com.glovoapp.backender.domain.*;
import com.glovoapp.backender.dto.OrderVM;
import com.glovoapp.backender.dto.SlotVM;
import com.glovoapp.backender.service.CourierService;
import com.glovoapp.backender.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.glovoapp.backender.util.DistanceCalculator.calculateDistance;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@Service
public class OrderServiceImp implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);

    @Value("${orders.in.km.distance:5.0}")
    private double distanceLimit;
    @Value("${glovo.orders.slot.meter.distance.range:500}")
    private int slotDistanceRange;
    @Value("${glovo.orders.slot.vip.priority:1}")
    private int vipPriority;
    @Value("${glovo.orders.slot.food.priority:2}")
    private int foodPriority;
    @Value("${glovo.orders.slot.other.priority:3}")
    private int otherPriority;
    @Value("${glovo.box.words}")
    private String[] glovoBoxWords;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CourierService courierService;


    @Override
    public List<OrderVM> findAll() {

        logger.info("Get all orders as OrderVM");

        return orderRepository.findAll().stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(toList());
    }

    @Override
    public List<SlotVM> findOrdersByCourierId(String courierId) {


        Courier courier = courierService.findById(courierId);
        logger.info("Find Orders which are close to Courier:{}", courier);
        Location courierLocation = courier.getLocation();

        //List<OrderDistance> orderDistances = spatialService.getOrdersAround(courierLocation);

        Map<Integer, List<OrderDistance>> groupedOrderDistanceMap = orderRepository.findAll().stream()
                //Map orders into OrderDistance which keeps distances too
                .map(order -> new OrderDistance(order, calculateDistance(courierLocation, order.getPickup())))
                //Sort OrderDistances using distance
                .sorted(comparing(OrderDistance::getDistance))
                //Filter orders far from courier who is using BICYCLE
                .filter(o -> !courier.getVehicle().equals(Vehicle.BICYCLE) || isInDistanceLimit(o.getDistance()))
                //Filters orders which need glovo box but courier doesn't have
                .filter(o -> courier.getBox() || !isBoxNeeded(o.getOrder().getDescription()))
                //Group orders using their distances which map into ranges such as 0-500,500-1000
                .collect(groupingBy(o -> getDistanceAsRange(o.getDistance()), TreeMap::new, mapping(e -> e, toList())));


        List<SlotVM> slotList = new ArrayList<>();

        /*
         *Event sort orders using their distances, there is another sort condition
         * which is type of the orders
         * Such as VIP, FOOD, OTHERS
         */
        logger.info("Preparing slots");
        groupedOrderDistanceMap.entrySet().forEach(ordersWithRanges -> {
            List<OrderDistance> sortedOrders = sortOrdersByPriorities(ordersWithRanges.getValue());
            slotList.add(new SlotVM(sortedOrders, rangeFormat(ordersWithRanges.getKey())));
        });

        return slotList;
    }

    /**
     * Gives range of distances considering slotDistance
     * Such 345->500, 600->1000, 10->500
     *
     * @param distance
     * @return
     */
    private int getDistanceAsRange(double distance) {
        int div = (int) (distance * 1000 / slotDistanceRange);
        return (div * slotDistanceRange);
    }

    /**
     * If the description of the order contains the words pizza,
     * cake or flamingo, we can only show the order to the courier
     * if they are equipped with a Glovo box.
     *
     * @param orderDescription
     * @return
     */
    private boolean isBoxNeeded(String orderDescription) {
        return Arrays.stream(glovoBoxWords).anyMatch(orderDescription.toUpperCase()::contains);
    }

    /**
     * If the order is further than 5km to the courier,
     * we will only show it to couriers that move in
     * motorcycle or electric scooter.
     *
     * @param distance
     * @return
     */
    private boolean isInDistanceLimit(double distance) {
        return distance < distanceLimit;
    }


    private String rangeFormat(int range) {
        return String.format("%d - %d m.", range, range + slotDistanceRange);
    }


    /**
     * Sort orders considering their priorities
     * For example VIP customer first,then food and then others
     *
     * @param orderDistances
     * @return
     */
    private List<OrderDistance> sortOrdersByPriorities(List<OrderDistance> orderDistances) {

        /*Keeps orders and their priorities.
         * TreeMap keeps data in order using keys
         */

        Map<Integer, List<OrderDistance>> listMap = orderDistances.stream()
                .collect(groupingBy(order -> getPriority(order.getOrder()), TreeMap::new, mapping(Function.identity(), toList())));

        /*
         * There are three list which consists of vip,food and other
         * Here we are flatting them into single list
         */
        return listMap.values().stream().flatMap(Collection::stream).collect(toList());
    }

    /**
     * Gives priorities to order considering parameter which read from property file
     *
     * @param order
     * @return
     */
    private int getPriority(Order order) {
        if (order.getVip()) {
            return vipPriority;
        } else if (order.getFood()) {
            return foodPriority;
        } else {
            return otherPriority;
        }
    }
}
