package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(Long id){
        super(MessageFormat.format("Could not find car with id: {0}",id));
    }
}
