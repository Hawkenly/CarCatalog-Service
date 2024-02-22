package org.example.carCatalog.service;

import org.example.carCatalog.model.CarModels;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CheckMark {
    public static CarModels checkMark(String mark,String audi, String ac){
        if(Objects.equals(mark, "AUDI"))
            return new CarModels(audi);
        else if (Objects.equals(mark, "AC"))
            return new CarModels(ac);
        else
            return new CarModels("Incorrect ID");

    }
}
