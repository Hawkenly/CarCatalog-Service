package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class CarNotFoundException extends RuntimeException {
    /**
     * Конструктор - создание сообщения исключения.
     * @param id - id автомобиля
     */
    public CarNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find car with id: {0}", id));
    }
}
