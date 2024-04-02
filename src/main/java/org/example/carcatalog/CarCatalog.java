package org.example.carcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarCatalog {
    /**
     * To hide utility class constructor.
     */
    private final CarCatalog carCatalog = new CarCatalog();
    /**
     * Точка входа в программу.
     * @param args - агрументы командной строки
     */
    public static void main(final String[] args) {
        SpringApplication.run(CarCatalog.class, args);
    }
}
