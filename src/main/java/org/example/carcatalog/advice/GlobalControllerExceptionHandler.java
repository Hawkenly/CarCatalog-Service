package org.example.carcatalog.advice;

import org.example.carcatalog.aspect.AspectAnnotation;
import org.example.carcatalog.model.exception.CarNotFoundException;
import org.example.carcatalog.model.exception.ColorNotFoundException;
import org.example.carcatalog.model.exception.ModelIsAlreadyAssignedException;
import org.example.carcatalog.model.exception.ModelIsNotAssignedException;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    /**
     * @param modelNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<String> handleModelNotFound(
            final ModelNotFoundException modelNotFoundException) {
        return new ResponseEntity<>(modelNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param carNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> handleCarNotFound(
            final CarNotFoundException carNotFoundException) {
        return new ResponseEntity<>(carNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param colorNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ColorNotFoundException.class)
    public ResponseEntity<String> handleColorNotFound(
            final ColorNotFoundException colorNotFoundException) {
        return new ResponseEntity<>(colorNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param modelIsAlreadyAssignedException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelIsAlreadyAssignedException.class)
    public ResponseEntity<String> handleModelIsAlreadyAssigned(
            final ModelIsAlreadyAssignedException
                    modelIsAlreadyAssignedException) {
        return new ResponseEntity<>(
                modelIsAlreadyAssignedException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param modelIsNotAssignedException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelIsNotAssignedException.class)
    public ResponseEntity<String> handleModelIsNotAssigned(
            final ModelIsNotAssignedException modelIsNotAssignedException) {
        return new ResponseEntity<>(modelIsNotAssignedException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String errorMessage = String.format("%s - %s",
                    fieldError.getField(), fieldError.getDefaultMessage());
            errors.add(errorMessage);
        });
        String errorMessage = String.join(", ", errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(
            final Exception exception) {
        return new ResponseEntity<>(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
