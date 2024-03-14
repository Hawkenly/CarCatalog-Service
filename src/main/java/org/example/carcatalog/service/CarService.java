package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.CarNotFoundException;
import org.example.carcatalog.model.exception.ModelIsAlreadyAssignedException;
import org.example.carcatalog.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarModelService carModelService;
    private final CarColorService carColorService;
   public List<Car> getAllCars(){
       return carRepository.findAll();
   }

   public Car getCar(Long id) {
       return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
   }

   public Car saveCar(Car car){
       return carRepository.save(car);
   }

   @Transactional
   public Car updateCar(Long id, Car car){
       Car carToUpdate = getCar(id);
       carToUpdate.setName(car.getName());
       carToUpdate.setPopular(car.getPopular());
       carToUpdate.setCountry(car.getCountry());
       return carToUpdate;
   }

   public void removeCar(Long id){
       Car car = getCar(id);
       carRepository.delete(car);
   }

   @Transactional
   public Car addModelToCar(Long carId, Long modelId){
       Car car = getCar(carId);
       CarModel carModel = carModelService.getModel(modelId);
       if(Objects.nonNull(carModel.getCar())){
            throw new ModelIsAlreadyAssignedException(modelId,
                    carModel.getCar().getId());
       }
       car.addModel(carModel);
       carModel.setCar(car);
       return car;
   }
   @Transactional
   public void removeModelFromCar(Long carId, Long modelId){
       Car car = getCar(carId);
       CarModel carModel = carModelService.getModel(modelId);
       car.removeModel(carModel);
   }
   @Transactional
   public Car addColorToCar(Long carId, Long colorId){
       Car car = getCar(carId);
       CarColor carColor = carColorService.getColor(colorId);
       List<Car> cars= carColor.getCars();
       int index =-1;
       index= cars.indexOf(car);
       if (index !=-1)
           return car;

       car.addColor(carColor);
       carColor.addCar(car);
       return car;
   }

   @Transactional
   public void removeColorFromCar(Long carId, Long colorId){
       Car car = getCar(carId);
       CarColor carColor = carColorService.getColor(colorId);

       car.removeColor(carColor);
       carColor.removeCar(car);
   }
}
