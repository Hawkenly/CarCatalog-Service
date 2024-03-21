package org.example.carcatalog.repository;

import org.example.carcatalog.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModel,Long> {

    @Query("select p from CarModel p where p.car.id=:id")
    List<CarModel> getCarModelsByCar(Long id);

    @Query(value = "SELECT * FROM models p WHERE p.car_id=:id", nativeQuery = true)
    List<CarModel> getCarModelsByCarNative(Long id);
}
