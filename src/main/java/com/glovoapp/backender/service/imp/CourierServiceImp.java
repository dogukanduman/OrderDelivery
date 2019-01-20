package com.glovoapp.backender.service.imp;

import com.glovoapp.backender.dao.CourierRepository;
import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.exception.ResourceNotFoundException;
import com.glovoapp.backender.service.CourierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierServiceImp implements CourierService {
    private static final Logger logger = LoggerFactory.getLogger(CourierServiceImp.class);

    @Autowired
    private CourierRepository courierRepository;

    @Override
    public Courier findById(String courierId) {

        logger.info("Get courier by id:{}", courierId);

        Courier courier = courierRepository.findById(courierId);
        if (courier == null) {
            throw new ResourceNotFoundException("There is no courier with id:" + courierId);
        }
        return courier;
    }

    @Override
    public List<Courier> findAll() {

        logger.info("Get all couriers");
        return courierRepository.findAll();
    }
}
