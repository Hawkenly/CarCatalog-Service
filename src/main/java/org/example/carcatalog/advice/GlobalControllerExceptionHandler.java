package org.example.carcatalog.advice;

import org.example.carcatalog.model.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    /**
     * @param modelNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleModelNotFound(
            final ModelNotFoundException modelNotFoundException,
            final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found: " + modelNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param carNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCarNotFound(
            final CarNotFoundException carNotFoundException,
            final  WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found: " + carNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param colorNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ColorNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleColorNotFound(
            final ColorNotFoundException colorNotFoundException,
            final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found: " + colorNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param modelIsAlreadyAssignedException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelIsAlreadyAssignedException.class)
    public ResponseEntity<ErrorDetails> handleModelIsAlreadyAssigned(
            final ModelIsAlreadyAssignedException
                    modelIsAlreadyAssignedException, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                modelIsAlreadyAssignedException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * @param modelIsNotAssignedException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelIsNotAssignedException.class)
    public ResponseEntity<ErrorDetails> handleModelIsNotAssigned(
            final ModelIsNotAssignedException modelIsNotAssignedException,
            final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                modelIsNotAssignedException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception,
            final WebRequest request) {
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String errorMessage = String.format("%s - %s",
                    fieldError.getField(), fieldError.getDefaultMessage());
            errors.add(errorMessage);
        });
        String errorMessage = String.join(",", errors);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Resource not found: " + errorMessage,
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            final Exception exception, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Resource not found: " + exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
