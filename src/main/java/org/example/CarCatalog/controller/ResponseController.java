package org.example.CarCatalog.controller;

import org.example.CarCatalog.model.CarModels;
import org.example.CarCatalog.service.CheckMark;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cars")
public class ResponseController {
    String AUDI="{id: AUDI, name: AUDI, popular: true, country: Германия}";
    String AC="{id: AC, name: AC, popular:false, country: Великобритания)";
    @GetMapping("mark")
    public CarModels getResponse(@RequestParam(value = "mark", required = false, defaultValue = "AUDI") String mark){
        return CheckMark.checkMark(mark,AUDI,AC);
    }

}