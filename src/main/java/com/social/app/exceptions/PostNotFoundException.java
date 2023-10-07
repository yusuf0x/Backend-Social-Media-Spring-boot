package com.social.app.exceptions;

import java.io.Serial;

public class PostNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public PostNotFoundException(String message){
        super(message);
    }
}
