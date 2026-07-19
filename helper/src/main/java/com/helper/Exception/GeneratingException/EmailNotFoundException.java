package com.helper.Exception.GeneratingException;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String message)
    {
        super(message);
    }
}   
