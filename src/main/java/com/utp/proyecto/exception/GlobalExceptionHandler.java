package com.utp.proyecto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // 404
    // =========================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(
            ResourceNotFoundException exception) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", 404);
        respuesta.put("error", "Recurso no encontrado");
        respuesta.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(respuesta);
    }

    // =========================
    // 400 - VALIDACIONES
    // =========================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException exception) {

        Map<String, String> errores = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errores.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", 400);
        respuesta.put("error", "Error de validación");
        respuesta.put("details", errores);

        return ResponseEntity
                .badRequest()
                .body(respuesta);
    }

    // =========================
    // 400 - ARGUMENTOS
    // =========================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarArgumentoInvalido(
            IllegalArgumentException exception) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", 400);
        respuesta.put("error", "Solicitud incorrecta");
        respuesta.put("message", exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(respuesta);
    }

    // =========================
    // 409 - CONFLICTO
    // =========================

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> manejarEstadoInvalido(
            IllegalStateException exception) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", 409);
        respuesta.put("error", "Conflicto");
        respuesta.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuesta);
    }
}
