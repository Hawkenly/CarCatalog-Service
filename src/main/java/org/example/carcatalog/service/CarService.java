package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.aspect.AspectAnnotation;
import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.*;
import org.example.carcatalog.repository.CarColorRepository;
import org.example.carcatalog.repository.CarModelRepository;
import org.example.carcatalog.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final CarColorRepository carColorRepository;
    private final SimpleCache<String, Object> carSimpleCache;

    @AspectAnnotation
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    @AspectAnnotation
    public Car getCar(final Long id) {
        Car car;
        if (carSimpleCache.containsKey(id.toString())) {
            car = (Car) carSimpleCache.get(id.toString());
        } else {
            car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
            carSimpleCache.put(id.toString(), car);
        }
        return car;
    }
    @AspectAnnotation
    public Car saveCar(final Car car) {
       carRepository.save(car);
       carSimpleCache.put(car.getId().toString(), car);
       return car;
   }

    @AspectAnnotation
    @Transactional
    public Car updateCar(final Long id, final Car car) {
        Car carToUpdate = getCar(id);
        carSimpleCache.remove(id.toString());
        carToUpdate.setName(car.getName());
        carToUpdate.setPopular(car.getPopular());
        carToUpdate.setCountry(car.getCountry());
        carSimpleCache.put(id.toString(), car);
        return carToUpdate;
    }

    @AspectAnnotation
    public void removeCar(final Long id) {
        Car car = getCar(id);
        carRepository.delete(car);
        carSimpleCache.remove(id.toString());
   }

    @AspectAnnotation
   @Transactional
   public Car addModelToCar(final Long carId, final Long modelId) {
       Car car = getCar(carId);
       CarModel carModel = carModelRepository.findById(modelId).orElseThrow(() -> new ModelNotFoundException(modelId));
       if (Objects.nonNull(carModel.getCar())) {
            throw new ModelIsAlreadyAssignedException(modelId,
                    carModel.getCar().getId());
       }
       car.addModel(carModel);
       carModel.setCar(car);
       carSimpleCache.remove(carId.toString());
       carSimpleCache.put(carId.toString(), car);
       return car;
   }
    @AspectAnnotation
   @Transactional
   public void removeModelFromCar(final Long carId, final Long modelId) {
       Car car = getCar(carId);
       CarModel carModel = carModelRepository.findById(modelId).orElseThrow(() -> new ModelNotFoundException(modelId));
       if (Objects.isNull(carModel.getCar())) {
           throw new ModelIsNotAssignedException(modelId);
       }
       car.removeModel(carModel);
       carModel.setCar(null);
       carSimpleCache.clear();
       carRepository.save(car);
   }
    @AspectAnnotation
   @Transactional
   public Car addColorToCar(final Long carId, final Long colorId) {
       Car car = getCar(carId);
       CarColor carColor = carColorRepository.findById(colorId).orElseThrow(() -> new ColorNotFoundException(colorId));
       List<Car> cars = carColor.getCars();
       int index = -1;
       index = cars.indexOf(car);
       if (index != 1) {
           return car;
       }
       car.addColor(carColor);
       carColor.addCar(car);
       return car;
   }

   @AspectAnnotation
   @Transactional
   public void removeColorFromCar(final Long carId, final Long colorId) {
       Car car = getCar(carId);
       CarColor carColor = carColorRepository.findById(colorId).orElseThrow(() -> new ColorNotFoundException(colorId));
       car.removeColor(carColor);
       carColor.removeCar(car);
   }

   @AspectAnnotation
    public List<Car> bulkSave(final List<Car> cars) {
       carRepository.saveAll(cars);
       return cars;
   }
}
