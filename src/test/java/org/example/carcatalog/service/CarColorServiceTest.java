package org.example.carcatalog.service;

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

    private static List<CarColor> list;

    private CarColor carColor;

    private final Long carColorId = 52L;

    private static final int NUM_OF_REPEATS = 5;

    @BeforeEach
    void setUp() {
        carColor = new CarColor();
        carColor.setId(carColorId);
        carColor.setColor("black...");
    }

    @BeforeAll
    static void setUpList(){
        list = new ArrayList<>();
        for(int i = 0; i < NUM_OF_REPEATS; i++) {
            CarColor carColor = new CarColor();
            carColor.setId((long) i);
            carColor.setColor("black" + i);
            list.add(carColor);
        }
    }

    @Test
    public void testGetAllColors() {
        Mockito.when(carColorRepository.findAll()).thenReturn(list);
        List<CarColor> resultList = carColorService.getAllColors();
        assertEquals(resultList, list);
    }

    @Test
    public void testGetColorById() {
        Mockito.when(carColorRepository.findById(carColorId)).thenReturn(Optional.of(carColor));

        CarColor result = carColorService.getColor(carColorId);

        assertNotNull(result);
        assertEquals(result,carColor);
    }

    @Test
    public void testGetColorByIdException() {
        Mockito.when(carColorRepository.findById(carColorId)).thenReturn(Optional.empty());

        assertThrows(ColorNotFoundException.class, () -> carColorService.getColor(carColorId));
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

        Mockito.when(carColorRepository.findById(carColorId)).thenReturn(Optional.of(carColor));
        CarColor result = carColorService.updateColor(carColorId, carColor);
        assertEquals(result, carColor);
    }

    @Test
    public void testUpdateColorException(){
        Mockito.when(carColorRepository.findById(carColorId)).thenReturn(Optional.empty());
        assertThrows(ColorNotFoundException.class, () -> carColorService.updateColor(carColorId, carColor));
    }

    @Test
    public void testDeleteColor(){
        Mockito.when(carColorRepository.findById(carColorId)).thenReturn(Optional.of(carColor));
        carColorService.removeColor(carColorId);
        Mockito.verify(carColorRepository, Mockito.times(1)).delete(carColor);
    }

    @Test
    public void testDeleteColorException(){
       Mockito.when(carColorRepository.findById(carColorId)).thenReturn(Optional.empty());
       assertThrows(ColorNotFoundException.class, () -> carColorService.removeColor(carColorId));
    }
}
