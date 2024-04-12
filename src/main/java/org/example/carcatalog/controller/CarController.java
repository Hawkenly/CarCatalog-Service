
package org.example.carcatalog.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.carcatalog.model.Car;
import org.example.carcatalog.service.CarService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cars")
@AllArgsConstructor
public  class CarController {
    /**
     * Поле автомобильный сервис.
     */
    private final CarService carService;
    /**
     * Метод обработки запроса на получения всех автомобилей.
     * @return возвращает список всех автомобилей
     */
    @GetMapping("/")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
    /**
     * Метод обработки запроса на получение автомобиля по id.
     * @param id - id автомобиля
     * @return возвращает автомобиль
     */
    @GetMapping
    public Car getCar(@RequestParam final Long id) {
        return carService.getCar(id);
    }
    /**
     * Метод обработки запроса на добавление автомобиля.
     * @param car - автомобиль
     * @return возвращает добавленный автомобиль
     */
    @PostMapping("add")
    public Car saveCar(@Valid @RequestBody final Car car) {
        return carService.saveCar(car);
    }
    /**
     * Метод обработки запроса на обновление автомобиля.
     * @param id - id автомобиля, который нужно обновить
     * @param car - новый автомобиль
     * @return возвращает изменённый автомобиль
     */
    @PutMapping("update")
    public Car updateCar(@RequestParam final Long id,
                         @Valid @RequestBody final Car car) {
        return carService.updateCar(id, car);
    }
    /**
     * Метод обработки запроса на удаление автомобиля.
     * @param id - id автомобиля
     */
    @DeleteMapping("delete")
    public void removeCar(@RequestParam final Long id) {
        carService.removeCar(id);
    }
    /**
     * Метод обработки запроса на добавление модели к автомобилю.
     * @param carId - id автомобиля
     * @param modelId - id модели
     * @return возвращает изменённый автомобиль
     */
    @PostMapping("{carId}/models/{modelId}/add")
    public Car addModelToCar(@PathVariable final Long carId,
                             @PathVariable final Long modelId) {
        return carService.addModelToCar(carId, modelId);
    }
    /**
     * Метод обработки запроса на удаленик модели из автомобиля.
     * @param carId - id автомобиля
     * @param modelId - id модели
     */
    @DeleteMapping("{carId}/models/{modelId}/remove")
    public void removeModelFromCar(@PathVariable final Long carId,
                                   @PathVariable final Long modelId) {
        carService.removeModelFromCar(carId, modelId);
    }
    /**
     * Метод обработки запроса на добавление цвета к автомобилю.
     * @param carId - id автомобиля
     * @param colorId - id цвета
     * @return возвращает изменённый автомобиль
     */
    @PostMapping("{carId}/colors/{colorId}/add")
    public Car addColorToCar(@PathVariable final Long carId,
                             @PathVariable final Long colorId) {
        return carService.addColorToCar(carId, colorId);
    }
    /**
     * Метод обработки запроса на удаление цвета из автомобиля.
     * @param carId - id автомобиля
     * @param colorId - id цвета
     */
    @DeleteMapping("{carId}/colors/{colorId}/remove")
    public void removeColorFromCar(@PathVariable final Long carId,
                                   @PathVariable final Long colorId) {
        carService.removeColorFromCar(carId, colorId);
    }

    @PostMapping("bulk")
    public List<Car> bulkSave(@RequestBody final List<Car> cars) {
        carService.bulkSave(cars);
        return cars;
    }
}
