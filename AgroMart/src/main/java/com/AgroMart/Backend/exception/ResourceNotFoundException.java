package com.AgroMart.Backend.exception;

public class ResourceNotFoundException extends RuntimeException{

    public  ResourceNotFoundException(String message){ // Status Code 404
        super(message);


    }
}
