package com.glovoapp.backender.dao.imp;

import com.glovoapp.backender.dao.CourierRepository;
import com.glovoapp.backender.domain.Courier;
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
public class CourierRepositoryImp implements CourierRepository {
    private static final String COURIERS_FILE = "/couriers.json";
    private static List<Courier> couriers;


    /**
     * Init data
     */
    @PostConstruct
    public void init() {
        try (Reader reader = new InputStreamReader(CourierRepositoryImp.class.getResourceAsStream(COURIERS_FILE))) {
            Type type = new TypeToken<List<Courier>>() {
            }.getType();
            couriers = new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Courier courier) {
        couriers.add(courier);
    }

    @Override
    public void deleteAll() {
        couriers = new ArrayList<>();
    }

    public Courier findById(String courierId) {
        return couriers.stream()
                .filter(courier -> courierId.equals(courier.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Courier> findAll() {
        return new ArrayList<>(couriers);
    }


}
