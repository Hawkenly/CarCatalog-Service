package org.example.carcatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "colors")
public final class CarColor {
    /**
     * Поле id цвета.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Поле название цвета.
     */
    private String color;
    /**
     * Поле список автомобилей.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "colors")
    private List<Car> cars = new ArrayList<>();
    /**
     * Метод добавления автомобиля.
     * @param car - автомобиль
     */
    public void addCar(final Car car) {
        cars.add(car);
    }
    /**
     * Метод удаления автомобиля.
     * @param car - автомобиль
     */
    public void removeCar(final Car car) {
        cars.remove(car);
    }
}
