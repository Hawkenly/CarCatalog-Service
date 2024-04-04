package org.example.carcatalog.advice;

import org.example.carcatalog.model.exception.CarNotFoundException;
import org.example.carcatalog.model.exception.ColorNotFoundException;
import org.example.carcatalog.model.exception.ErrorDetails;
import org.example.carcatalog.model.exception.ModelIsAlreadyAssignedException;
import org.example.carcatalog.model.exception.ModelIsNotAssignedException;
import org.example.carcatalog.model.exception.ModelNotFoundException;
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
     * Поле константы.
     */
    private static final String NOT_FOUND = "Resource not found: ";
    /**
     * @param modelNotFoundException - исключение
     * @param request - веб-запрос
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleModelNotFound(
            final ModelNotFoundException modelNotFoundException,
            final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND + modelNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param carNotFoundException - исключение
     * @param request - веб-запрос
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCarNotFound(
            final CarNotFoundException carNotFoundException,
            final  WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND + carNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param colorNotFoundException - исключение
     * @param request - веб-запрос
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ColorNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleColorNotFound(
            final ColorNotFoundException colorNotFoundException,
            final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND + colorNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.NOT_FOUND);
    }
    /**
     * @param modelIsAlreadyAssignedException - исключение
     * @param request - веб-запрос
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
     * @param request - веб-запрос
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

    /**
     * @param exception - исключение
     * @param request - веб-запрос
     * @return возвращает информацию об исключении
     */
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
                errorMessage,
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception - исключение
     * @param request - веб-запрос
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            final Exception exception, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
