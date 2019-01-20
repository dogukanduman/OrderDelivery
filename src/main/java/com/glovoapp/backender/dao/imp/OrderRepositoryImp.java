package com.glovoapp.backender.dao.imp;

import com.glovoapp.backender.dao.OrderRepository;
import com.glovoapp.backender.domain.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepositoryImp implements OrderRepository {
    private static final String ORDERS_FILE = "/orders.json";

    private static List<Order> orders;

    /**
     * Init data
     */
    @PostConstruct
    public void init() {
        try (Reader reader = new InputStreamReader(OrderRepositoryImp.class.getResourceAsStream(ORDERS_FILE))) {
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            orders = new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public void deleteAll() {
        orders = new ArrayList<>();
    }
}
