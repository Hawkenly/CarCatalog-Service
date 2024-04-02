package org.example.carcatalog.advice;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.carcatalog.model.exception.CarNotFoundException;
import org.example.carcatalog.model.exception.ColorNotFoundException;
import org.example.carcatalog.model.exception.ModelIsAlreadyAssignedException;
import org.example.carcatalog.model.exception.ModelIsNotAssignedException;
import org.example.carcatalog.model.exception.ModelNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public final class GlobalControllerExceptionHandler
        extends ResponseEntityExceptionHandler {

    /**
     * Поле logger.
     */
    private static final Logger MY_LOGGER = LogManager.
            getLogger(GlobalControllerExceptionHandler.class);

    /**
     * @param modelNotFoundException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<String> handleModelNotFound(
            final ModelNotFoundException modelNotFoundException) {
        MY_LOGGER.info(modelNotFoundException.getMessage());
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
        MY_LOGGER.info(carNotFoundException.getMessage());
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
        MY_LOGGER.info(colorNotFoundException.getMessage());
        return new ResponseEntity<>(colorNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final @NonNull HttpRequestMethodNotSupportedException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        MY_LOGGER.info("Please change your http method type");
        return new ResponseEntity<>("Please change your http method type",
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final @NonNull HttpMessageNotReadableException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        MY_LOGGER.info("Please check your http request body");
        return new ResponseEntity<>("Please check your http request body",
                HttpStatus.BAD_REQUEST);
    }

    /**
     * @param modelIsAlreadyAssignedException - исключение
     * @return возвращает информацию об исключении
     */
    @ExceptionHandler(ModelIsAlreadyAssignedException.class)
    public ResponseEntity<String> handleModelIsAlreadyAssigned(
            final ModelIsAlreadyAssignedException
                    modelIsAlreadyAssignedException) {
        MY_LOGGER.info(modelIsAlreadyAssignedException.getMessage());
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
        MY_LOGGER.info(modelIsNotAssignedException.getMessage());
        return new ResponseEntity<>(modelIsNotAssignedException.getMessage(),
                HttpStatus.NOT_FOUND);
    }
}
