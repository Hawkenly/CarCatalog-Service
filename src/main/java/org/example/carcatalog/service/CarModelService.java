package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.aspect.AspectAnnotation;
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

    @AspectAnnotation
    public List<CarModel> getAllModels() {
        return carModelRepository.findAll();
    }

    @AspectAnnotation
    public CarModel getModel(final Long id) {
        CarModel carModel;
        if (modelSimpleCache.containsKey(id.toString())) {
            carModel = (CarModel) modelSimpleCache.get(id.toString());
        } else {
            carModel = carModelRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(id));
            modelSimpleCache.put(id.toString(), carModel);
        }
        return carModel;
    }

    @AspectAnnotation
    public List<CarModel> getCarModelsByCar(final Long id) {
        return carModelRepository.getCarModelsByCar(id);
    }
    @AspectAnnotation
    public List<CarModel> getCarModelsByCarNative(final Long id) {
        return carModelRepository.getCarModelsByCarNative(id);
    }

    @AspectAnnotation
    public CarModel saveModel(final CarModel model) {
        carModelRepository.save(model);
        if (!modelSimpleCache.containsKey(model.getId().toString())) {
            modelSimpleCache.put(model.getId().toString(), model);
        }
        return model;
    }

    @AspectAnnotation
    @Transactional
    public CarModel updateModel(final Long id, final CarModel model) {
        CarModel modelToUpdate = getModel(id);
        Car car = modelToUpdate.getCar();
        if (Objects.nonNull(car)) {
            car.removeModel(modelToUpdate);
            car.addModel(model);
        }
        modelSimpleCache.remove(id.toString());
        modelToUpdate.setModel(model.getModel());
        modelSimpleCache.put(id.toString(), modelToUpdate);
        return modelToUpdate;
    }

    @AspectAnnotation
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
