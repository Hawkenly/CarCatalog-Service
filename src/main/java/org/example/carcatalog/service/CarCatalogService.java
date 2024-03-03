package org.example.carcatalog.service;

import org.example.carcatalog.model.Car;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CarCatalogService {
    public Car checkMark(String mark, String audi, String ac){
        String undefined="undefined";
        if(Objects.equals(mark, "AUDI")) {
            String[] parts=audi.split("[ ,]");
            return Car.builder().id(parts[1]).name(parts[3]).popular(parts[5]).country(parts[7]).build();
        }
        else if (Objects.equals(mark, "AC")) {
            String[] parts=ac.split("[ ,]");
            return Car.builder().id(parts[1]).name(parts[3]).popular(parts[5]).country(parts[7]).build();
        }
        else
            return Car.builder().id(undefined).name(undefined).popular(undefined).country(undefined).build();
    }
}
