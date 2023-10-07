package com.social.app.exceptions;

import java.io.Serial;

public class LikeNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public LikeNotFoundException(String message){
        super(message);
    }
}
