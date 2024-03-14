package org.example.carcatalog.model.exception;

import java.text.MessageFormat;
public class ColorNotFoundException extends RuntimeException {
    public ColorNotFoundException(Long id){
        super(MessageFormat.format("Could not find color with id: {0}",id));
    }
}
