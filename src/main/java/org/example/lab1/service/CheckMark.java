package org.example.lab1.service;

import org.example.lab1.model.CarModels;
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
