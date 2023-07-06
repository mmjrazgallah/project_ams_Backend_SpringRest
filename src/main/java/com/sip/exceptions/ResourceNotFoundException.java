package com.sip.exceptions;



public class ResourceNotFoundException extends IllegalArgumentException {

    public ResourceNotFoundException (String mesage){
        super(mesage);
    }
}
