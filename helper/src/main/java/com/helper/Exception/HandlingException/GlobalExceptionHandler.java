package com.helper.Exception.HandlingException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import org.springframework.validation.FieldError;

import java.util.HashMap;
// import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;

import com.helper.Exception.GeneratingException.CodeforcesEmailNotMatchException;
import com.helper.Exception.GeneratingException.InvalidPasswordException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;

import com.helper.Exception.GeneratingException.CodeforcesEmailNotFoundException;
import com.helper.Exception.GeneratingException.CodeforcesHandlerNotExist;
import com.helper.Exception.GeneratingException.EmailNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();
        Map<String,String> errorMessages = new HashMap<>();

        for(FieldError fe : bindingResult.getFieldErrors()){
            errorMessages.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errorMessages);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error","Email already exists");
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUserNotFoundException(EmailNotFoundException ex){
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String,String>> handleInvalidPasswordException(InvalidPasswordException ex){
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleBadCredentialException(BadCredentialsException ex)
    {
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error","Your Password Was Incorrect, Please Enter Valid Password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String,String>> handleHttpClientErrorException(HttpClientErrorException ex)
    {
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error","Codeforces Handler Is Not Exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(CodeforcesEmailNotMatchException.class)
    public ResponseEntity<Map<String,String>> handleEmailNotMatchException(CodeforcesEmailNotMatchException ex)
    {
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(CodeforcesEmailNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleEmailNotFoundException(CodeforcesEmailNotFoundException ex)
    {
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }


}
