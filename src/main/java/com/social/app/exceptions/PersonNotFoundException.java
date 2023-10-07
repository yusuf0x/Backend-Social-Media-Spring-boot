package com.social.app.exceptions;

import java.io.Serial;

public class PersonNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public PersonNotFoundException(String message){
        super(message);
    }
}
