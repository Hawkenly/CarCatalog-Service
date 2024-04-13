package org.example.carcatalog.service;

import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.CarNotFoundException;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.example.carcatalog.repository.CarColorRepository;
import org.example.carcatalog.repository.CarModelRepository;
import org.example.carcatalog.repository.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private SimpleCache<String, Object> carSimpleCache;

    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private CarColorRepository carColorRepository;

    @InjectMocks
    private CarService carService;

    private static List<Car> carList;

    private Car car;

    private CarModel carModel;

    private CarColor carColor;

    private final Long carId = 100L;

    private final Long modelId = 101L;

    private final Long colorId = 102L;

    private static final int NUM_OF_REPEATS = 5;

    @BeforeEach
    void setUp(){
        car = new Car();
        car.setId(carId);
        car.setName("testName");
        car.setPopular("testPopular");
        car.setCountry("testCountry");

        carModel = new CarModel();
        carModel.setId(modelId);
        carModel.setModel("testModel");

        carColor = new CarColor();
        carColor.setId(colorId);
        carColor.setColor("testColor");
    }

    @BeforeAll
    static void setUpList(){
        carList = new ArrayList<>();
        for(int i=0; i<NUM_OF_REPEATS; i++){
            Car car = new Car();
            car.setId((long)i);
            car.setName("testName" + i);
            car.setPopular("testPopular" + i);
            car.setCountry("testCountry" + i);
            carList.add(car);
        }
    }

    @Test
    public void testGetAllCars(){
        Mockito.when(carRepository.findAll()).thenReturn(carList);
        List<Car> result = carService.getAllCars();
        assertEquals(result, carList);
    }

    @Test
    public void testGetCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Car result = carService.getCar(carId);
        assertNotNull(result);
        assertEquals(result, car);
    }

    @Test
    public void testGetCarException(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.getCar(carId));
    }

    @Test
    public void testSaveCar(){
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Car result = carService.saveCar(car);
        assertNotNull(result);
        assertEquals(result, car);
        Mockito.verify(carRepository, Mockito.times(1)).save(car);
    }

    @Test
    public void testUpdateCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Car result = carService.updateCar(carId, car);
        assertEquals(result, car);
    }

    @Test
    public void testUpdateCarException(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.updateCar(carId, car));
    }

    @Test
    public void testRemoveCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        carService.removeCar(carId);
        Mockito.verify(carRepository, Mockito.times(1)).delete(car);
    }

    @Test
    public void testRemoveCarException(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.removeCar(carId));
    }
/*
    @Test
    public void testAddModelToCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));

        Car result = carService.addModelToCar(carId, modelId);
        assertEquals(result, car);
    }

    @Test
    public void testAddModelToCarExceptionCarNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.addModelToCar(carId, modelId));
    }

    @Test
    public void testAddModelToCarExceptionModelNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carService.addModelToCar(carId, modelId));
    }

    @Test
    public void testRemoveModelFromCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        Mockito.when(carRepository.save(car)).thenReturn(car);

        carService.removeModelFromCar(carId, modelId);
        Mockito.verify(carRepository, Mockito.times(1)).save(car);
    }

    @Test
    public void testRemoveModelFromCarExceptionCarNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.addModelToCar(carId, modelId));
    }

    @Test
    public void testRemoveModelFromCarExceptionModelNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carService.addModelToCar(carId, modelId));
    }*/

}
