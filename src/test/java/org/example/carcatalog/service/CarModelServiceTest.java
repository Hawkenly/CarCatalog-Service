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
public class CarModelServiceTest {
    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private SimpleCache<String, Object> modelSimpleCache;

    @InjectMocks
    private CarModelService carModelService;

    private static List<CarModel> modelList;

    private CarModel carModel;

    private final Long modelId = 1L;

    private final Long carId = 52L;

    private static final int NUM_OF_REPEATS = 5;

    @BeforeEach
    void setUp(){
        carModel = modelList.get(0);

        Car car = new Car();
        car.setId(carId);
        car.setName("testName");
        car.setPopular("testPopular");
        car.setCountry("testCountry");

        car.setModels(modelList);
        carModel.setCar(car);

        modelSimpleCache.put(modelId.toString(), carModel);
    }

    @BeforeAll
    static void setUpList(){
        modelList = new ArrayList<>();
        for(int i=0; i<NUM_OF_REPEATS; i++){
            CarModel carModel = new CarModel();
            carModel.setId((long)i+1);
            carModel.setModel("testModel" + i);
            modelList.add(carModel);
        }
    }

    @Test
    public void testGetAllModels(){
        Mockito.when(carModelRepository.findAll()).thenReturn(modelList);
        List<CarModel> result = carModelService.getAllModels();
        assertEquals(result, modelList);
    }

    @Test
    public void testGetModel(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        CarModel result = carModelService.getModel(modelId);
        assertNotNull(result);
        assertEquals(result, carModel);
    }

    @Test
    public void testGetModelException(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carModelService.getModel(modelId));
    }

    @Test
    public void testGetCarModelsByCar(){
        Mockito.when(carModelRepository.getCarModelsByCar(carId)).thenReturn(modelList);
        List<CarModel> result = carModelService.getCarModelsByCar(carId);
        assertEquals(result, modelList);
    }

    @Test
    public void testGetCarModelsByCarNative(){
        Mockito.when(carModelRepository.getCarModelsByCarNative(carId)).thenReturn(modelList);
        List<CarModel> result = carModelService.getCarModelsByCarNative(carId);
        assertEquals(result, modelList);
    }

    @Test
    public void testSaveModel(){
        Mockito.when(carModelRepository.save(carModel)).thenReturn(carModel);
        CarModel result = carModelService.saveModel(carModel);
        assertNotNull(result);
        assertEquals(result, carModel);
        Mockito.verify(carModelRepository, Mockito.times(1)).save(carModel);
    }

    @Test
    public void testUpdateModel(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        CarModel result = carModelService.updateModel(modelId, carModel);
        assertEquals(result, carModel);
    }

    @Test
    public void testUpdateModelException(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carModelService.updateModel(modelId, carModel));
    }

    @Test
    public void testRemoveModel(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.of(carModel));
        carModelService.removeModel(modelId);
        Mockito.verify(carModelRepository, Mockito.times(1)).delete(carModel);
    }

    @Test
    public void testRemoveModelException(){
        Mockito.when(carModelRepository.findById(modelId)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> carModelService.removeModel(modelId));
    }
}
