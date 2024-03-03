package org.example.carcatalog.controller;

import org.example.carcatalog.model.Car;
import org.example.carcatalog.service.CarCatalogService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarCatalogController {
    CarCatalogService service;
    String audi="id: AUDI,name: AUDI,popular: true,country: Германия";
    String ac="id: AC,name: AC,popular: false,country: Великобритания";
    @GetMapping
    public Car getResponseMark(@RequestParam(value = "mark", required = false, defaultValue = "AUDI") String mark){
        return service.checkMark(mark,audi,ac);
    }
    @GetMapping("/")
    public List<Car> getResponseAll(){
        return List.of(
                service.checkMark("AUDI",audi,ac),
                service.checkMark("AC",audi,ac)
        );
        }
    }
