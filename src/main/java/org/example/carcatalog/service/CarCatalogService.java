package org.example.carcatalog.service;

import org.example.carcatalog.model.Car;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CarCatalogService {
    private CarCatalogService(){}
    public static Car checkMark(String mark, String audi, String ac){
        if(Objects.equals(mark, "AUDI"))
            return new Car(audi);
        else if (Objects.equals(mark, "AC"))
            return new Car(ac);
        else
            return new Car("Incorrect ID");

    }
}
