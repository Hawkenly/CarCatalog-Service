package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.example.carcatalog.repository.CarModelRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CarModelService {

    private final CarModelRepository carModelRepository;
    private final SimpleCache<String, Object> modelSimpleCache;
    private static final Logger MY_LOGGER = LogManager.getLogger(CarModelService.class);

    public List<CarModel> getAllModels() {
        MY_LOGGER.info("All car models were received from a DB.");
        return carModelRepository.findAll();
    }

    public CarModel getModel(Long id) {
        CarModel carModel;
        if (modelSimpleCache.containsKey(id.toString())) {
            carModel = (CarModel) modelSimpleCache.get(id.toString());
        } else {
            carModel = carModelRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(id));
            modelSimpleCache.put(id.toString(), carModel);
        }
        MY_LOGGER.info(MessageFormat.format("Car model with id: {0} was received from a DB.", id));
        return carModel;
    }

    public List<CarModel> getCarModelsByCar(Long id) {
        MY_LOGGER.info(MessageFormat.format("All models from car with id: {0} were received from a DB.", id));
        return carModelRepository.getCarModelsByCar(id);
    }

    public List<CarModel> getCarModelsByCarNative(Long id) {
        MY_LOGGER.info(MessageFormat.format("All models from car with id: {0} were received from a DB.", id));
        return carModelRepository.getCarModelsByCarNative(id);
    }

    public CarModel saveModel(CarModel model) {
        carModelRepository.save(model);
        if (!modelSimpleCache.containsKey(model.getId().toString())) {
            modelSimpleCache.put(model.getId().toString(), model);
        }
        MY_LOGGER.info("Model was saved in a DB.");
        return model;
    }

    @Transactional
    public CarModel updateModel(Long id, CarModel model) {
        CarModel modelToUpdate = getModel(id);
        Car car = modelToUpdate.getCar();
        if (Objects.nonNull(car)) {
            car.removeModel(modelToUpdate);
            car.addModel(model);
        }
        modelSimpleCache.remove(id.toString());
        modelToUpdate.setModel(model.getModel());
        modelSimpleCache.put(id.toString(), modelToUpdate);
        MY_LOGGER.info(MessageFormat.format("Model with id: {0} was updated in a DB.", id));
        return modelToUpdate;
    }

    public void removeModel(Long id) {
        CarModel model = getModel(id);
        Car car = model.getCar();
        if (Objects.nonNull(car)) {
            car.removeModel(model);
        }
        carModelRepository.delete(model);
        MY_LOGGER.info(MessageFormat.format("Model with id: {0} was removed from a DB.", id));
        modelSimpleCache.remove(id.toString());
    }
}
