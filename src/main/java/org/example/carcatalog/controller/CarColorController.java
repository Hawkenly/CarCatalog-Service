package org.example.carcatalog.controller;

import lombok.AllArgsConstructor;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.service.CarColorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("colors")
@AllArgsConstructor
public final class CarColorController {
    /**
     * Поле сервис цвета автомобиля.
     */
    private final CarColorService carColorService;
    /**
     * Метод обработки запроса на получения всех цветов.
     * @return возвращает список всех цветов
     */
    @GetMapping("/")
    public List<CarColor> getAllCars() {
        return carColorService.getAllColors();
    }
    /**
     * Метод обработки запроса на получение цвета по id.
     * @param id - id модели
     * @return возвращает цвет
     */
    @GetMapping
    public CarColor getColor(@RequestParam final Long id) {
        return carColorService.getColor(id);
    }
    /**
     * Метод обработки запроса на добавление цвета.
     * @param color - цвет
     * @return возвращает добавленный цвет
     */
    @PostMapping("add")
    public CarColor saveColor(@RequestBody final CarColor color) {
        return carColorService.saveColor(color);
    }
    /**
     * Метод обработки запроса на обновление цвета.
     * @param id - id цвета, который нужно обновить
     * @param color - новый цвет
     * @return возвращает изменённый цвет
     */
    @PutMapping("update")
    public CarColor updateColor(@RequestParam final Long id,
                                @RequestBody final CarColor color) {
        return carColorService.updateColor(id, color);
    }
    /**
     * Метод обработки запроса на удаление цвета.
     * @param id - id цвета
     */
    @DeleteMapping("delete")
    public void removeColor(@RequestParam final Long id) {
        carColorService.removeColor(id);
    }
}
