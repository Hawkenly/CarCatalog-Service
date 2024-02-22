package org.example.carCatalog.service;

import org.example.carCatalog.model.Car;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CarCatalogService {
    public static Car checkMark(String mark, String audi, String ac){
        if(Objects.equals(mark, "AUDI"))
            return new Car(audi);
        else if (Objects.equals(mark, "AC"))
            return new Car(ac);
        else
            return new Car("Incorrect ID");

    }
}
