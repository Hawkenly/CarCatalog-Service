package org.example.carcatalog.service;

import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.*;
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
class CarServiceTest {

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

    private CarModel carModel, carModelWithCar;

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
        carModel.setModelName("testModel");

        carColor = new CarColor();
        carColor.setId(colorId);
        carColor.setColorName("testColor");

        carModelWithCar = new CarModel();
        carModelWithCar.setId(modelId);
        carModelWithCar.setModelName("testModel");

        carModelWithCar.setCar(carList.get(0));
        carList.get(0).addModel(carModelWithCar);

    }

    @BeforeAll
    static void setUpList(){
        carList = new ArrayList<>();
        for(int i=0; i<NUM_OF_REPEATS; i++){
            Car car = new Car();
            car.setId((long)i+1);
            car.setName("testName" + i);
            car.setPopular("testPopular" + i);
            car.setCountry("testCountry" + i);
            carList.add(car);
        }
    }

    @Test
    void testGetAllCars(){
        Mockito.when(carRepository.findAll()).thenReturn(carList);
        List<Car> result = carService.getAllCars();
        assertEquals(result, carList);
    }

    @Test
    void testGetCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Car result = carService.getCar(carId);
        assertNotNull(result);
        assertEquals(result, car);
    }

    @Test
    void testGetCarException(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.getCar(carId));
    }

    @Test
    void testSaveCar(){
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Car result = carService.saveCar(car);
        assertNotNull(result);
        assertEquals(result, car);
        Mockito.verify(carRepository, Mockito.times(1)).save(car);
    }

    @Test
    void testUpdateCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Car result = carService.updateCar(carId, car);
        assertEquals(result, car);
    }

    @Test
    void testUpdateCarException(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.updateCar(carId, car));
    }

    @Test
    void testRemoveCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        carService.removeCar(carId);
        Mockito.verify(carRepository, Mockito.times(1)).delete(car);
    }

    @Test
    void testRemoveCarException(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.removeCar(carId));
    }

    @Test
    void testAddModelToCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));

        Car result = carService.addModelToCar(carId, modelId);
        assertEquals(result, car);
    }

    @Test
    void testAddModelWithCarToCar(){
        Mockito.when(carRepository.findById(carList.get(0).getId())).thenReturn(Optional.of(carList.get(0)));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModelWithCar));
        Long carId =carList.get(0).getId();
        assertThrows(ModelIsAlreadyAssignedException.class, () -> carService.addModelToCar(carId, modelId));
    }

    @Test
    void testAddModelToCarExceptionCarNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.addModelToCar(carId, modelId));
    }

    @Test
    void testAddModelToCarExceptionModelNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carService.addModelToCar(carId, modelId));
    }

    @Test
    void testRemoveModelFromCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        assertThrows(ModelIsNotAssignedException.class, () -> carService.removeModelFromCar(carId, modelId));
    }

    @Test
    void testRemoveModelWithCarFromCar(){
        Mockito.when(carRepository.findById(carList.get(0).getId())).thenReturn(Optional.of(carList.get(0)));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModelWithCar));
        Mockito.when(carRepository.save(carList.get(0))).thenReturn(carList.get(0));

        carService.removeModelFromCar(carList.get(0).getId(), modelId);
        Mockito.verify(carRepository, Mockito.times(1)).save(carList.get(0));
    }

    @Test
    void testRemoveModelFromCarExceptionCarNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.removeModelFromCar(carId, modelId));
    }

    @Test
    void testRemoveModelFromCarExceptionModelNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carService.removeModelFromCar(carId, modelId));
    }

    @Test
    void testAddColorToCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.of(carColor));

        Car result = carService.addColorToCar(carId, colorId);
        assertEquals(result, car);
    }

    @Test
    void testAddColorToCarExceptionCarNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.addColorToCar(carId, colorId));
    }

    @Test
    void testAddColorToCarExceptionColorNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.empty());
        assertThrows(ColorNotFoundException.class, () -> carService.addColorToCar(carId, colorId));
    }

    @Test
    void testRemoveColorFromCar(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.of(carColor));
        carService.removeColorFromCar(carId, colorId);
        Mockito.verify(carRepository, Mockito.times(1)).findById(carId);
    }

    @Test
    void testRemoveColorFromCarExceptionCarNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.removeColorFromCar(carId, colorId));
    }

    @Test
    void testRemoveColorFromCarExceptionColorNotFound(){
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.empty());
        assertThrows(ColorNotFoundException.class, () -> carService.removeColorFromCar(carId, colorId));
    }

    @Test
    void testBulkSave(){
        Mockito.when(carRepository.saveAll(carList)).thenReturn(carList);
        List<Car> result = carService.bulkSave(carList);
        assertEquals(result, carList);
        Mockito.verify(carRepository, Mockito.times(1)).saveAll(carList);
    }
}
