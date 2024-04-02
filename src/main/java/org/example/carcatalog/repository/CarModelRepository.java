package org.example.carcatalog.repository;

import org.example.carcatalog.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    /**
     * Метод получения списка моделей автомобиля по id.
     * @param id - id автомобиля
     * @return возвращает список моделей автомобиля
     */
    @Query("select p from CarModel p where p.car.id=:id")
    List<CarModel> getCarModelsByCar(Long id);

    /**
     * Метод получения списка моделей автомобиля по id. (Native)
     * @param id - id автомобиля
     * @return возвращает список моделей автомобиля
     */
    @Query(value = "SELECT * FROM models p WHERE p.car_id=:id",
            nativeQuery = true)
    List<CarModel> getCarModelsByCarNative(Long id);
}
