package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class ModelIsNotAssignedException extends RuntimeException {
    /**
     * Конструктор - создание сообщения исключения.
     * @param modelId - id модели
     */
    public ModelIsNotAssignedException(final Long modelId) {
        super(MessageFormat.format("Model: {0} is not assigned to any car",
                modelId));
    }
}
