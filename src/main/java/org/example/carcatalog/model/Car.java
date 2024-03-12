package org.example.carcatalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String popular;
    private String country;
    @OneToMany
    @JoinColumn(name = "car_id")
    private List<CarModel> models = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name = "car_color",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<CarColor> colors = new ArrayList<>();

    public void addModel(CarModel model){
        models.add(model);
    }

    public void removeModel(CarModel model){
        models.remove(model);
    }

    public void addColor(CarColor color){
        colors.add(color);
    }

    public void removeColor(CarColor color){
        colors.remove(color);
    }
}
