package com.glovoapp.backender.controller;

import com.glovoapp.backender.dto.OrderVM;
import com.glovoapp.backender.dto.SlotVM;
import com.glovoapp.backender.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
class API {

    @Value("${backender.welcome_message}")
    private String welcomeMessage;

    private final OrderService orderService;

    @Autowired
    public API(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/")
    @ResponseBody
    String root() {
        return welcomeMessage;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<List<OrderVM>> orders() {
        List<OrderVM> orderVMList = orderService.findAll();
        return new ResponseEntity<>(orderVMList, HttpStatus.OK);

    }

    @RequestMapping(value = "/orders/{courierId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<List<SlotVM>> ordersByCourierId(@PathVariable String courierId) {

        List<SlotVM> slotList = orderService.findOrdersByCourierId(courierId);
        return new ResponseEntity<>(slotList, HttpStatus.OK);
    }

}
