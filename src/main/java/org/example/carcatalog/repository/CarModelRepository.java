package org.example.carcatalog.repository;

import org.example.carcatalog.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModel,Long> {
}
