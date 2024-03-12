package org.example.carcatalog.controller;

import lombok.AllArgsConstructor;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.service.CarColorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colors")
@AllArgsConstructor
public class CarColorController {

    private final CarColorService carColorService;

    @GetMapping("/")
    public List<CarColor> getAllCars(){
        return carColorService.getAllColors();
    }
    @GetMapping
    public CarColor getColor(@RequestParam Long id){
        return carColorService.getColor(id);
    }

    @PostMapping("add")
    public CarColor saveColor(@RequestBody CarColor color){
        return carColorService.saveColor(color);
    }

    @PutMapping("update")
    public CarColor updateColor(@RequestParam Long id,@RequestBody CarColor color){
        return carColorService.updateColor(id, color);
    }

    @DeleteMapping("delete")
    public void removeColor(@RequestParam Long id){
        carColorService.removeColor(id);
    }


}
