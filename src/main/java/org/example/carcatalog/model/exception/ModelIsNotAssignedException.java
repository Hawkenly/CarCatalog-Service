package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class ModelIsNotAssignedException extends RuntimeException{

    public ModelIsNotAssignedException(final Long modelId){
        super(MessageFormat.format("Model: {0} is not assigned to any car",modelId));
    }
}
