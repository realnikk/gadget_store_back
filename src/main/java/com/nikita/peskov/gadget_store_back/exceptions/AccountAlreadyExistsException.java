package com.nikita.peskov.gadget_store_back.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String message){
        super(message);
    }
}
