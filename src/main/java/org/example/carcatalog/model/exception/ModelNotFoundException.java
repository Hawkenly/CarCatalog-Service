package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class ModelNotFoundException extends RuntimeException{

    public ModelNotFoundException (Long id){
        super(MessageFormat.format("Could not find model with id: {0}",id));
    }
}
