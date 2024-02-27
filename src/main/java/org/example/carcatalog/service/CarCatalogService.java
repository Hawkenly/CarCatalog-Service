package org.example.carcatalog.service;

import org.example.carcatalog.model.Car;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CarCatalogService {
    private CarCatalogService(){}
    public static Car checkMark(String mark, String audi, String ac){
        String UNDEFINED="undefined";
        if(Objects.equals(mark, "AUDI")) {
            String[] parts=audi.split("[ ,]");
            return new Car(parts[1],parts[3],parts[5],parts[7]);
        }
        else if (Objects.equals(mark, "AC")) {
            String[] parts=ac.split("[ ,]");
            return new Car(parts[1],parts[3],parts[5],parts[7]);
        }
        else
            return new Car(UNDEFINED,UNDEFINED,UNDEFINED,UNDEFINED);
    }
}
