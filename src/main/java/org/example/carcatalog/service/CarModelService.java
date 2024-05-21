package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.cache.SimpleCache;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.example.carcatalog.repository.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CarModelService {

    private final CarModelRepository carModelRepository;
    private final SimpleCache<String, Object> modelSimpleCache;

    public List<CarModel> getAllModels() {
        return carModelRepository.findAll();
    }
    public CarModel getModel(final Long id) {
        CarModel carModel;
        if (modelSimpleCache.containsKey(id.toString())) {
            carModel = (CarModel) modelSimpleCache.get(id.toString());
        } else {
            carModel = carModelRepository.findById(id)
                    .orElseThrow(() -> new ModelNotFoundException(id));
            modelSimpleCache.put(id.toString(), carModel);
        }
        return carModel;
    }
    public List<CarModel> getCarModelsByCar(final Long id) {
        return carModelRepository.getCarModelsByCar(id);
    }
    public List<CarModel> getCarModelsByCarNative(final Long id) {
        return carModelRepository.getCarModelsByCarNative(id);
    }
    public CarModel saveModel(final CarModel model) {
        if (carModelRepository.findByModelName(model.getModelName()) == null) {
            carModelRepository.save(model);
        }
        if (!modelSimpleCache.containsValue(model)) {
            modelSimpleCache.put(model.getId().toString(), model);
        }
        return carModelRepository.findByModelName(model.getModelName());
    }
    @Transactional
    public CarModel updateModel(final Long id, final CarModel model) {
        CarModel modelToUpdate = carModelRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(id));
        Car car = modelToUpdate.getCar();
        if (Objects.nonNull(car)) {
            car.removeModel(modelToUpdate);
            modelSimpleCache.remove(id.toString());
            modelToUpdate.setModelName(model.getModelName());
            modelSimpleCache.put(id.toString(), modelToUpdate);
            car.addModel(modelToUpdate);
        } else {
            modelSimpleCache.remove(id.toString());
            modelToUpdate.setModelName(model.getModelName());
            modelSimpleCache.put(id.toString(), modelToUpdate);
        }
        return modelToUpdate;
    }
    public void removeModel(final Long id) {
        CarModel model = getModel(id);
        Car car = model.getCar();
        if (Objects.nonNull(car)) {
            car.removeModel(model);
        }
        carModelRepository.delete(model);
        modelSimpleCache.remove(id.toString());
    }
}
