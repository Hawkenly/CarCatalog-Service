package org.example.carcatalog.controller;

import lombok.AllArgsConstructor;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
@AllArgsConstructor
public class    CarController {

    private final CarService carService;

    @GetMapping("/")
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }
    @GetMapping
    public Car getCar(@RequestParam Long id){
        return carService.getCar(id);
    }

    @PostMapping("add")
    public Car saveCar(@RequestBody Car car){
        return carService.saveCar(car);
    }

    @PutMapping("update")
    public Car updateCar(@RequestParam Long id,@RequestBody Car car){
        return carService.updateCar(id, car);
    }

    @DeleteMapping("delete")
    public void removeCar(@RequestParam Long id){
        carService.removeCar(id);
    }

    @PostMapping("{carId}/models/{modelId}/add")
    public Car addModelToCar(@PathVariable Long carId, @PathVariable Long modelId){
        return carService.addModelToCar(carId,modelId);
    }

    @DeleteMapping("{carId}/models/{modelId}/remove")
    public void removeModelFromCar(@PathVariable Long carId, @PathVariable Long modelId){
        carService.removeModelFromCar(carId,modelId);
    }

    @PostMapping("{carId}/colors/{colorId}/add")
    public Car addColorToCar(@PathVariable Long carId, @PathVariable Long colorId){
        return carService.addColorToCar(carId,colorId);
    }

    @DeleteMapping("{carId}/colors/{colorId}/remove")
    public void removeColorFromCar(@PathVariable Long carId, @PathVariable Long colorId){
        carService.removeColorFromCar(carId,colorId);
    }

}
