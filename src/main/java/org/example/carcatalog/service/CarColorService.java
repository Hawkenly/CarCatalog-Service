package org.example.carcatalog.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.model.exception.ColorNotFoundException;
import org.example.carcatalog.repository.CarColorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarColorService {

    private final CarColorRepository carColorRepository;
    public List<CarColor> getAllColors(){
        return carColorRepository.findAll();
    }

    public CarColor getColor(Long id) {
        return carColorRepository.findById(id).orElseThrow(() -> new ColorNotFoundException(id));
    }

    public CarColor saveColor(CarColor color){
        return carColorRepository.save(color);
    }

    @Transactional
    public CarColor updateColor(Long id, CarColor color){
        CarColor colorToUpdate = getColor(id);
        colorToUpdate.setColor(color.getColor());
        return colorToUpdate;
    }

    public void removeColor(Long id){
        CarColor color = getColor(id);
        carColorRepository.delete(color);
    }
}
