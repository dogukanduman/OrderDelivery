package com.glovoapp.backender.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.glovoapp.backender.domain.OrderDistance;

import java.util.List;

/**
 * Created by Dogukan Duman on 16.01.2019.
 * SlotVM keeps grouped orders by their distances
 */
@JsonTypeName("slot")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class SlotVM {

    @JsonProperty(value = "orderList")
    private List<OrderDistance> orderDistances;

    /**
     * Name of the slot which shows what is the distance range of them
     * For example 500,1000,1500
     */
    @JsonProperty(value = "range")
    private String range;

    public SlotVM(List<OrderDistance> orderDistances, String range) {
        this.orderDistances = orderDistances;
        this.range = range;
    }

    public List<OrderDistance> getOrderDistances() {
        return orderDistances;
    }

    public void setOrderDistances(List<OrderDistance> orderDistances) {
        this.orderDistances = orderDistances;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
