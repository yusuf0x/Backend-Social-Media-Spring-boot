package com.social.app.exceptions;

import java.io.Serial;

public class FollowerNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public FollowerNotFoundException(String message){
        super(message);
    }
}
