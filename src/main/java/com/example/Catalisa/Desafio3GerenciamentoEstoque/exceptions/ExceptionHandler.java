package com.example.Catalisa.Desafio3GerenciamentoEstoque.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Objects;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleFieldValidation(MethodArgumentNotValidException e) {
    String errorMessage = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
    return ResponseEntity.badRequest().body(errorMessage);
  }


}
