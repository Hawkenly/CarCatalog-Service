package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.exception.ColorNotFoundException;
import org.example.carcatalog.repository.CarColorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarColorService {

    private final CarColorRepository carColorRepository;
    private static final Logger MY_LOGGER = LogManager.getLogger(CarColorService.class);
    public List<CarColor> getAllColors() {
        MY_LOGGER.info("All car colors were received from a DB.");
        return carColorRepository.findAll();
    }

    public CarColor getColor(Long id) {
        CarColor color = carColorRepository.findById(id).orElseThrow(() -> new ColorNotFoundException(id));
        MY_LOGGER.info("Car color with id:" + id + "was received from a DB.");
        return color;
    }

    public CarColor saveColor(CarColor color) {
        carColorRepository.save(color);
        MY_LOGGER.info("Car color was saved a DB.");
        return color;
    }

    @Transactional
    public CarColor updateColor(Long id, CarColor color) {
        CarColor colorToUpdate = getColor(id);
        colorToUpdate.setColor(color.getColor());
        MY_LOGGER.info("Color with id:" + id + "was updated in a DB.");
        return colorToUpdate;
    }

    public void removeColor(Long id) {
        CarColor color = getColor(id);
        List<Car> cars = color.getCars();
        for (Car car: cars) {
            car.getColors().remove(color);
        }
        carColorRepository.delete(color);
        MY_LOGGER.info("Color with id:" + id + "was removed from a DB.");
    }
}
