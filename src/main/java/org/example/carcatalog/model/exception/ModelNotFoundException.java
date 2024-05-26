package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class ModelNotFoundException extends RuntimeException {
    /**
     * Конструктор - создание сообщения исключения.
     * @param id - id модели
     */
    public ModelNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find model with id: {0}", id));
    }
}
