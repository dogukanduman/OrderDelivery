package com.glovoapp.backender.service;

import com.glovoapp.backender.dto.OrderVM;
import com.glovoapp.backender.dto.SlotVM;

import java.util.List;

public interface OrderService {

    /**
     * Get all Orders as OrderVM
     *
     * @return
     */
    List<OrderVM> findAll();

    /**
     * Get all orders close to given courier and map them into SlotVM
     *
     * @param courierId
     * @return
     */
    List<SlotVM> findOrdersByCourierId(String courierId);
}
