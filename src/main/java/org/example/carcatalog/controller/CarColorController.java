package org.example.carcatalog.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.carcatalog.aspect.AspectAnnotation;
import org.example.carcatalog.model.CarColor;
import org.example.carcatalog.service.CarColorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colors")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CarColorController {
    /**
     * Поле сервис цвета автомобиля.
     */
    private final CarColorService carColorService;
    /**
     * Метод обработки запроса на получения всех цветов.
     * @return возвращает список всех цветов
     */
    @AspectAnnotation
    @GetMapping("/")
    public List<CarColor> getAllCars() {
        return carColorService.getAllColors();
    }
    /**
     * Метод обработки запроса на получение цвета по id.
     * @param id - id модели
     * @return возвращает цвет
     */
    @AspectAnnotation
    @GetMapping
    public CarColor getColor(@RequestParam final Long id) {
        return carColorService.getColor(id);
    }
    /**
     * Метод обработки запроса на добавление цвета.
     * @param color - цвет
     * @return возвращает добавленный цвет
     */
    @AspectAnnotation
    @PostMapping("add")
    public CarColor saveColor(@Valid @RequestBody final CarColor color) {
        return carColorService.saveColor(color);
    }
    /**
     * Метод обработки запроса на обновление цвета.
     * @param id - id цвета, который нужно обновить
     * @param color - новый цвет
     * @return возвращает изменённый цвет
     */
    @AspectAnnotation
    @PutMapping("update")
    public CarColor updateColor(@RequestParam final Long id,
                                @Valid @RequestBody final CarColor color) {
        return carColorService.updateColor(id, color);
    }
    /**
     * Метод обработки запроса на удаление цвета.
     * @param id - id цвета
     */
    @AspectAnnotation
    @DeleteMapping("delete")
    public void removeColor(@RequestParam final Long id) {
        carColorService.removeColor(id);
    }
}
