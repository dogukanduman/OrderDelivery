package com.glovoapp.backender.service;

import com.glovoapp.backender.domain.Courier;

import java.util.List;

public interface CourierService {

    /**
     * Get Courier by id
     *
     * @param courierId
     * @return
     */
    Courier findById(String courierId);

    /**
     * Get all couriers
     *
     * @return
     */
    List<Courier> findAll();
}
