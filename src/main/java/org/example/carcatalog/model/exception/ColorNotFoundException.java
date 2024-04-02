
package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class ColorNotFoundException extends RuntimeException {
    /**
     * @param id - id цвета автомобиля
     */
    public ColorNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find color with id: {0}", id));
    }
}
