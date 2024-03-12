package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.example.carcatalog.repository.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarModelService {

    private final CarModelRepository carModelRepository;

    public List<CarModel> getAllModels(){
        return carModelRepository.findAll();
    }

    public CarModel getModel(Long id){
        return carModelRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(id) );
    }

    public CarModel saveModel(CarModel model){
        return carModelRepository.save(model);
    }
    @Transactional
    public CarModel updateModel(Long id, CarModel model){
        CarModel modelToUpdate = getModel(id);
        modelToUpdate.setModel(model.getModel());;
        return modelToUpdate;
    }

    public void removeModel(Long id){
        CarModel model = getModel(id);
        carModelRepository.delete(model);
    }
}
