package org.example.carcatalog.repository;

import org.example.carcatalog.model.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarColorRepository extends JpaRepository<CarColor,Long> {
}
