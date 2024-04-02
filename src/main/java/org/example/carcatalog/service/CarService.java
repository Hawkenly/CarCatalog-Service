package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final SimpleCache<String, Object> carSimpleCache;
    private static final Logger MY_LOGGER = LogManager.getLogger(CarService.class);

   public List<Car> getAllCars() {
       MY_LOGGER.info("All cars were received from a DB.");
       return carRepository.findAll();
   }
   public Car getCar(Long id) {
       Car car;
       if (carSimpleCache.containsKey(id.toString())) {
           car = (Car) carSimpleCache.get(id.toString());
       } else {
           car = carRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(id));
           carSimpleCache.put(id.toString(), car);
       }
       MY_LOGGER.info("Car with id:" + id + "was received from a DB.");
       return car;
   }
   public Car saveCar(Car car) {
       carRepository.save(car);
       carSimpleCache.put(car.getId().toString(), car);
       MY_LOGGER.info("Car was saved a DB.");
       return car;
   }

   @Transactional
   public Car updateCar(Long id, Car car) {
       Car carToUpdate = getCar(id);
       carSimpleCache.remove(id.toString());
       carToUpdate.setName(car.getName());
       carToUpdate.setPopular(car.getPopular());
       carToUpdate.setCountry(car.getCountry());
       carSimpleCache.put(id.toString(), car);
       MY_LOGGER.info("Car with id:" + id + "was updated in a DB.");
       return carToUpdate;
   }

   public void removeCar(Long id) {
       Car car = getCar(id);
       carRepository.delete(car);
       carSimpleCache.remove(id.toString());
       MY_LOGGER.info("Car with id:" + id + "was removed from a DB.");
   }

   @Transactional
   public Car addModelToCar(Long carId, Long modelId) {
       Car car = getCar(carId);
       CarModel carModel = carModelService.getModel(modelId);
       if (Objects.nonNull(carModel.getCar())) {
            throw new ModelIsAlreadyAssignedException(modelId,
                    carModel.getCar().getId());
       }
       car.addModel(carModel);
       carModel.setCar(car);
       carSimpleCache.remove(carId.toString());
       carSimpleCache.put(carId.toString(), car);
       MY_LOGGER.info("Model with id:" + modelId + "was added to a car with id:" + carId);
       return car;
   }
   @Transactional
   public void removeModelFromCar(Long carId, Long modelId) {
       Car car = getCar(carId);
       CarModel carModel = carModelService.getModel(modelId);
       if (Objects.isNull(carModel.getCar())) {
           throw new ModelIsNotAssignedException(modelId);
       }
       car.removeModel(carModel);
       carModel.setCar(null);
       carSimpleCache.clear();
       carRepository.save(car);
       MY_LOGGER.info("Model with id:" + modelId + "was removed from a car with id:" + carId);
   }
   @Transactional
   public Car addColorToCar(Long carId, Long colorId) {
       Car car = getCar(carId);
       CarColor carColor = carColorService.getColor(colorId);
       List<Car> cars = carColor.getCars();
       int index = -1;
       index = cars.indexOf(car);
       if (index != 1) {
           return car;
       }
       car.addColor(carColor);
       carColor.addCar(car);
       MY_LOGGER.info("Color with id:" + colorId + "was added to a car with id:" + carId);
       return car;
   }

   @Transactional
   public void removeColorFromCar(Long carId, Long colorId) {
       Car car = getCar(carId);
       CarColor carColor = carColorService.getColor(colorId);
       car.removeColor(carColor);
       carColor.removeCar(car);
       MY_LOGGER.info("Model with id:" + colorId + "was removed from a car with id:" + carId);
   }
}
