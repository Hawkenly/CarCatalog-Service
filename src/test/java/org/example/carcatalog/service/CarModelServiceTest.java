package org.example.carcatalog.service;

import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.example.carcatalog.repository.CarModelRepository;
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
class CarModelServiceTest {
    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private SimpleCache<String, Object> modelSimpleCache;

    @InjectMocks
    private CarModelService carModelService;

    private static List<CarModel> modelList;

    private CarModel carModel;

    private final Long modelId =33L;

    private final Long carId = 52L;

    private static final int NUM_OF_REPEATS = 5;

    @BeforeEach
    void setUp(){
        carModel = new CarModel();
        carModel.setId(modelId);
        carModel.setModelName("testModel");

        Car car = new Car();
        car.setId(carId);
        car.setName("testName");
        car.setPopular("testPopular");
        car.setCountry("testCountry");

        car.addModel(modelList.get(0));
        modelList.get(0).setCar(car);

        modelSimpleCache.put(modelId.toString(), carModel);
    }

    @BeforeAll
    static void setUpList(){
        modelList = new ArrayList<>();
        for(int i=0; i<NUM_OF_REPEATS; i++){
            CarModel carModel = new CarModel();
            carModel.setId((long)i+1);
            carModel.setModelName("testModel" + i);
            modelList.add(carModel);
        }
    }

    @Test
    void testGetAllModels(){
        Mockito.when(carModelRepository.findAll()).thenReturn(modelList);
        List<CarModel> result = carModelService.getAllModels();
        assertEquals(result, modelList);
    }

    @Test
    void testGetModel(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        CarModel result = carModelService.getModel(modelId);
        assertNotNull(result);
        assertEquals(result, carModel);
    }

    @Test
    void testGetModelNoCache(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        modelSimpleCache.clear();
        CarModel result = carModelService.getModel(modelId);
        assertNotNull(result);
        assertEquals(result, carModel);
    }

    @Test
    void testGetModelException(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carModelService.getModel(modelId));
    }

    @Test
    void testGetCarModelsByCar(){
        Mockito.when(carModelRepository.getCarModelsByCar(carId)).thenReturn(modelList);
        List<CarModel> result = carModelService.getCarModelsByCar(carId);
        assertEquals(result, modelList);
    }

    @Test
    void testGetCarModelsByCarNative(){
        Mockito.when(carModelRepository.getCarModelsByCarNative(carId)).thenReturn(modelList);
        List<CarModel> result = carModelService.getCarModelsByCarNative(carId);
        assertEquals(result, modelList);
    }

    @Test
    void testSaveModelNoInRep(){
        Mockito.when(carModelRepository.findByModelName(carModel.getModelName())).thenReturn(null);
        Mockito.when(carModelRepository.save(carModel)).thenReturn(carModel);

        CarModel result = carModelService.saveModel(carModel);
        assertNotNull(result);
        assertEquals(carModel, result);
        Mockito.verify(carModelRepository, Mockito.times(1)).save(carModel);
        Mockito.verify(carModelRepository, Mockito.times(1)).findByModelName(carModel.getModelName());
    }

    @Test
    void testSaveModelInRep(){
        Mockito.when(carModelRepository.findByModelName(carModel.getModelName())).thenReturn(carModel);

        CarModel result = carModelService.saveModel(carModel);

        assertNotNull(result);
        assertEquals(carModel, result);
        Mockito.verify(carModelRepository, Mockito.never()).save(carModel);
        Mockito.verify(carModelRepository, Mockito.times(2)).findByModelName(carModel.getModelName());
    }

    @Test
    void testUpdateModel(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        CarModel result = carModelService.updateModel(modelId, carModel);
        assertEquals(result, carModel);
    }

    @Test
    void testUpdateModelWithCar(){
        Mockito.when(carModelRepository.findById(modelList.get(0).getId())).thenReturn(Optional.of(modelList.get(0)));
        CarModel result = carModelService.updateModel(modelList.get(0).getId(), modelList.get(0));
        assertEquals(result, modelList.get(0));
    }

    @Test
    void testUpdateModelException(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carModelService.updateModel(modelId, carModel));
    }

    @Test
    void testRemoveModel(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        carModelService.removeModel(modelId);
        Mockito.verify(carModelRepository, Mockito.times(1)).delete(carModel);
    }

    @Test
    void testRemoveModelWithCar(){
        Mockito.when(carModelRepository.findById(modelList.get(0).getId())).thenReturn(Optional.of(modelList.get(0)));
        carModelService.removeModel(modelList.get(0).getId());
        Mockito.verify(carModelRepository, Mockito.times(1)).delete(modelList.get(0));
    }

    @Test
    void testRemoveModelException(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carModelService.removeModel(modelId));
    }
}
