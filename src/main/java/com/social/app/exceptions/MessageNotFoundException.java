package com.social.app.exceptions;

import java.io.Serial;

public class MessageNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public MessageNotFoundException(String message){
        super(message);
    }
}
