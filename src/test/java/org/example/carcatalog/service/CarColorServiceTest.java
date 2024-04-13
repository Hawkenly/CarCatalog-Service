package org.example.carcatalog.service;

import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.exception.ColorNotFoundException;
import org.example.carcatalog.repository.CarColorRepository;
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
public class CarColorServiceTest {

    @Mock
    private CarColorRepository carColorRepository;

    @InjectMocks
    private CarColorService carColorService;

    private static List<CarColor> colorList;

    private CarColor carColor;

    private final Long colorId = 52L;

    private static final int NUM_OF_REPEATS = 5;

    @BeforeEach
    void setUp() {
        carColor = new CarColor();
        carColor.setId(colorId);
        carColor.setColor("testColor");

        List<Car> carList = new ArrayList<>();

        for(int i = 0; i < NUM_OF_REPEATS; i++) {
            Car car = new Car();
            car.setId((long) i);
            car.setName("testCar");
            car.setPopular("testPopular");
            car.setCountry("testCountry");
            car.addColor(carColor);
            carList.add(car);
        }

        carColor.setCars(carList);
    }

    @BeforeAll
    static void setUpList(){
        colorList = new ArrayList<>();
        for(int i = 0; i < NUM_OF_REPEATS; i++) {
            CarColor carColor = new CarColor();
            carColor.setId((long) i);
            carColor.setColor("testColor" + i);
            colorList.add(carColor);
        }
    }

    @Test
    public void testGetAllColors() {
        Mockito.when(carColorRepository.findAll()).thenReturn(colorList);
        List<CarColor> resultList = carColorService.getAllColors();
        assertEquals(resultList, colorList);
    }

    @Test
    public void testGetColor() {
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.of(carColor));
        CarColor result = carColorService.getColor(colorId);
        assertNotNull(result);
        assertEquals(result,carColor);
    }

    @Test
    public void testGetColorException() {
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.empty());
        assertThrows(ColorNotFoundException.class, () -> carColorService.getColor(colorId));
    }

    @Test
    public void testSaveColor(){
        Mockito.when(carColorRepository.save(carColor)).thenReturn(carColor);
        CarColor result = carColorService.saveColor(carColor);
        assertNotNull(result);
        assertEquals(result,carColor);
        Mockito.verify(carColorRepository, Mockito.times(1)).save(carColor);

    }

    @Test
    public void testUpdateColor(){
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.of(carColor));
        CarColor result = carColorService.updateColor(colorId, carColor);
        assertEquals(result, carColor);
    }

    @Test
    public void testUpdateColorException(){
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.empty());
        assertThrows(ColorNotFoundException.class, () -> carColorService.updateColor(colorId, carColor));
    }

    @Test
    public void testRemoveColor(){
        Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.of(carColor));
        carColorService.removeColor(colorId);
        Mockito.verify(carColorRepository, Mockito.times(1)).delete(carColor);
    }

    @Test
    public void testRemoveColorException(){
       Mockito.when(carColorRepository.findById(colorId)).thenReturn(Optional.empty());
       assertThrows(ColorNotFoundException.class, () -> carColorService.removeColor(colorId));
    }
}
