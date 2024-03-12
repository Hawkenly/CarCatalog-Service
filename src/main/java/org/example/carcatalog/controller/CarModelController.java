package org.example.carcatalog.controller;

import lombok.AllArgsConstructor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.service.CarModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("models")
@AllArgsConstructor
public class CarModelController {

    private final CarModelService carModelService;

    @GetMapping("/")
    public List<CarModel> getAllModels(){
        return carModelService.getAllModels();
    }

    @GetMapping
    public CarModel getModel(@RequestParam Long id){
        return carModelService.getModel(id);
    }

    @PostMapping("add")
    public CarModel saveCar(@RequestBody CarModel model){
        return carModelService.saveModel(model);
    }

    @PutMapping("update")
    public CarModel updateCar(@RequestParam Long id,@RequestBody CarModel model){
        return carModelService.updateModel(id,model);
    }

    @DeleteMapping("delete")
    public void removeCar(@RequestParam Long id){
        carModelService.removeModel(id);
    }
}
