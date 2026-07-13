package com.banco.auth.exception;

import com.banco.auth.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Le indica a Spring que esta clase cacha errores de cualquier @RestController
public class GlobalExceptionHandler {

    /**
     * Captura los errores de validación de los DTOs (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationErrorHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errorsMap = new HashMap<>();

        // Recorremos todos los campos que fallaron y extraemos su mensaje personalizado
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nameField = ((FieldError) error).getField();
            String messageError = error.getDefaultMessage();
            errorsMap.put(nameField, messageError);
        });

        ErrorResponse errorDto = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation errors in the data received.",
                errorsMap
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura los errores de logica de negocio
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> businessLogicErrorHandler(RuntimeException ex) {
        ErrorResponse errorDto = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
