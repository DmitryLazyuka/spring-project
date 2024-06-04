package org.example.springproject.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(e -> ((FieldError) e).getField() + " " + e.getDefaultMessage())
                .collect(Collectors.toList());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
