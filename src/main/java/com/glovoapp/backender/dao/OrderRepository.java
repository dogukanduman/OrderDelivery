package com.glovoapp.backender.dao;

import com.glovoapp.backender.domain.Order;

import java.util.List;

public interface OrderRepository {

    /**
     * Get all orders
     *
     * @return
     */
    List<Order> findAll();

    /**
     * Add order
     *
     * @param order
     */
    void add(Order order);

    /**
     * Delete all orders
     */
    void deleteAll();

}
