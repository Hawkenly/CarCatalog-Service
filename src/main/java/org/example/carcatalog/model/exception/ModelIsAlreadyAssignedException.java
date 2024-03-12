package org.example.carcatalog.model.exception;

import java.text.MessageFormat;

public class ModelIsAlreadyAssignedException extends RuntimeException{
    public ModelIsAlreadyAssignedException(final Long modelId, final Long carId){
        super(MessageFormat.format("Model: {0} is already assigned to car: {1}", modelId, carId));
    }
}
