package com.glovoapp.backender.domain;

/**
 * Created by Dogukan Duman on 16.01.2019.
 * OrderDistance keeps orders and its distances to a specific location
 */

public class OrderDistance {

    private Order order;
    private double distance;

    public OrderDistance(Order order, double distance) {
        this.order = order;
        this.distance = distance;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "OrderDistance{" +
                "order=" + order +
                ", distance=" + distance +
                '}';
    }


}
