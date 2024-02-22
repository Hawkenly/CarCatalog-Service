package org.example.carCatalog.controller;

import org.example.carCatalog.model.Car;
import org.example.carCatalog.service.CarCatalogService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cars")
public class CarCatalogController {
    String audi="{id: AUDI, name: AUDI, popular: true, country: Германия}";
    String ac="{id: AC, name: AC, popular:false, country: Великобритания)";
    @GetMapping()
    public Car getResponseMark(@RequestParam(value = "mark", required = false, defaultValue = "AUDI") String mark){
        return CarCatalogService.checkMark(mark,audi,ac);
    }
    @GetMapping("/")
    public Car getResponse(){
        String response=audi+ac;
        return new Car(response);
    }
}