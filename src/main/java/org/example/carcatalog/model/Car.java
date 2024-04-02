package org.example.carcatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
public final class Car {
    /**
     * Поле id автомобиля (первичный ключ).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Поле название марки.
     */
    private String name;
    /**
     * Поле популярность.
     */
    private String popular;
    /**
     * Поле страна.
     */
    private String country;
    /**
     * Поле список моделей.
     */
    @OneToMany
    @JoinColumn(name = "car_id")
    private List<CarModel> models = new ArrayList<>();
    /**
     * Поле список цветов.
     */
    @ManyToMany
    @JoinTable(name = "car_color",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<CarColor> colors = new ArrayList<>();
    /**
     * Метод добавления модели.
     * @param model - модель автомобиля
     */
    public void addModel(final CarModel model) {
        models.add(model);
    }
    /**
     * Метод удаления модели.
     * @param model - модель автомобиля
     */
    public void removeModel(final CarModel model) {
        models.remove(model);
    }
    /**
     * Метод добавления цвета.
     * @param color - цвет автомобиля
     */
    public void addColor(final CarColor color) {
        colors.add(color);
    }
    /**
     * Метод удаления цвета.
     * @param color - цвет автомобиля
     */
    public void removeColor(final CarColor color) {
        colors.remove(color);
    }
    @Override
    public String toString() {
        return id.toString();
    }
}
