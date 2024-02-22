package org.example.CarCatalog.service;

import org.example.CarCatalog.model.CarModels;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CheckMark {
    public static CarModels checkMark(String mark,String AUDI, String AC){
        if(Objects.equals(mark, "AUDI"))
            return new CarModels(AUDI);
        else if (Objects.equals(mark, "AC"))
            return new CarModels(AC);
        else
            return new CarModels("Incorrect ID");

    }
}
