package org.example.carcatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "colors")
public class CarColor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String color;

    @JsonIgnore
    @ManyToMany( mappedBy = "colors")
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car){
        cars.add(car);
    }

    public void removeCar(Car car){
        cars.remove(car);
    }

}
