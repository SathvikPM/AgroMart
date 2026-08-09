package com.AgroMart.Backend.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public  ResourceNotFoundException(String message){
        super(message);
    }
}
