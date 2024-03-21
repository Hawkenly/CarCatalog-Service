package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.ModelIsAlreadyAssignedException;
import org.example.carcatalog.model.exception.ModelIsNotAssignedException;
import org.example.carcatalog.model.exception.ModelNotFoundException;
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
    private final SimpleCache<String,Object> carSimpleCache;

   public List<Car> getAllCars(){
       return carRepository.findAll();
   }

   public Car getCar(Long id) {
       Car car;
       if(carSimpleCache.containsKey(id.toString())) {
           car = (Car) carSimpleCache.get(id.toString());
           System.out.println("Read car from cache (get)");
       }
       else {
           car = carRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(id));
           carSimpleCache.put(id.toString(),car);
           System.out.println("Write car to cache (get)");
       }
       return car;
   }

   public Car saveCar(Car car){
       carRepository.save(car);
       carSimpleCache.put(car.getId().toString(),car);
       System.out.println("Write car to cache (post)");
       return car;
   }

   @Transactional
   public Car updateCar(Long id, Car car){
       Car carToUpdate = getCar(id);
       carSimpleCache.remove(id.toString());
       carToUpdate.setName(car.getName());
       carToUpdate.setPopular(car.getPopular());
       carToUpdate.setCountry(car.getCountry());
       carSimpleCache.put(id.toString(),car);
       return carToUpdate;
   }

   public void removeCar(Long id){
       Car car = getCar(id);
       carRepository.delete(car);
       carSimpleCache.remove(id.toString());
       System.out.println("Delete car from cache (delete)");
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
       carSimpleCache.remove(carId.toString());
       carSimpleCache.put(carId.toString(),car);
       return car;
   }
   @Transactional
   public void removeModelFromCar(Long carId, Long modelId){
       Car car = getCar(carId);
       CarModel carModel = carModelService.getModel(modelId);
       if(Objects.isNull(carModel.getCar())){
           throw new ModelIsNotAssignedException(modelId);
       }
       car.removeModel(carModel);
       carModel.setCar(null);
       carSimpleCache.clear();
       carRepository.save(car);

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
