package org.example.carcatalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    /**
     * Поле популярность.
     */
    @NotBlank
    private String popular;
    /**
     * Поле страна.
     */
    @NotBlank
    private String country;

    /**
     * Поле список моделей.
     */
    @OneToMany(fetch = FetchType.EAGER)
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
