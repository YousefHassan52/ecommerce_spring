package com.example.ecommerce_spring;

import com.example.ecommerce_spring.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnReadableException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Invalid Request body values"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException exceptions
    ){
        Map<String, String> errors = new HashMap<String,String>();
        exceptions.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
