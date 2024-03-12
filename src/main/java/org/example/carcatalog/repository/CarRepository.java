package org.example.carcatalog.repository;

import org.example.carcatalog.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car,Long> {
}
