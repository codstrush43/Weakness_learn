package com.helper.Exception.HandlingException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;
import org.springframework.validation.FieldError;
import java.util.HashMap;
// import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import com.helper.Exception.GeneratingException.UserNotFoundException;
import com.helper.Exception.GeneratingException.InvalidPasswordException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;


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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUserNotFoundException(UserNotFoundException ex){
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

}
