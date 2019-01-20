package com.glovoapp.backender.dao;

import com.glovoapp.backender.domain.Courier;

import java.util.List;

public interface CourierRepository {

    /**
     * Find courier by id
     *
     * @param courierId
     * @return
     */
    Courier findById(String courierId);

    /**
     * Get All couriers
     *
     * @return
     */
    List<Courier> findAll();

    /**
     * Add new courier to repository
     *
     * @param courier
     */
    void add(Courier courier);

    /**
     * Delete all Couriers;
     */
    void deleteAll();

}
