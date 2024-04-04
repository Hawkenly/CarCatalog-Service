package org.example.carcatalog.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.carcatalog.model.CarModel;
import org.example.carcatalog.service.CarModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("models")
@AllArgsConstructor
public  class CarModelController {
    /**
     * Поле сервис модели автомобиля.
     */
    private final CarModelService carModelService;
    /**
     * Метод обработки запроса на получения всех моделей.
     * @return возвращает список всех моделей
     */
    @GetMapping("/")
    public List<CarModel> getAllModels() {
        return carModelService.getAllModels();
    }
    /**
     * Метод обработки запроса на получение модели по id.
     * @param id - id модели
     * @return возвращает модель
     */
    @GetMapping
    public CarModel getModel(@RequestParam final Long id) {
        return carModelService.getModel(id);
    }
    /**
     * Метод обработки запроса на получение моделей автомобиля по id.
     * @param id - id автомобиля
     * @return возвращает список моделей автомобиля
     */
    @GetMapping("fromCar")
    public List<CarModel> getModelsByCar(@RequestParam final Long id) {
        return carModelService.getCarModelsByCar(id);
    }
    /**
     * Метод обработки запроса на получение моделей автомобиля по id (Native).
     * @param id - id автомобиля
     * @return возвращает список моделей автомобиля
     */
    @GetMapping("fromCarNative")
    public List<CarModel> getModelsByCarNative(@RequestParam final Long id) {
        return carModelService.getCarModelsByCarNative(id);
    }
    /**
     * Метод обработки запроса на добавление модели.
     * @param model - модель
     * @return возвращает добавленную модель
     */
    @PostMapping("add")
    public CarModel saveCar(@Valid @RequestBody final CarModel model) {
        return carModelService.saveModel(model);
    }
    /**
     * Метод обработки запроса на обновление модели.
     * @param id - id модели, которую нужно обновить
     * @param model - новая модель
     * @return возвращает изменённую модель
     */
    @PutMapping("update")
    public CarModel updateCar(@RequestParam final Long id,
                              @Valid @RequestBody final CarModel model) {
        return carModelService.updateModel(id, model);
    }
    /**
     * Метод обработки запроса на удаление модели.
     * @param id - id модели
     */
    @DeleteMapping("delete")
    public void removeCar(@RequestParam final Long id) {
        carModelService.removeModel(id);
    }
}
