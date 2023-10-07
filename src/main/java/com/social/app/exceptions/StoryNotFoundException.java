package com.social.app.exceptions;

import java.io.Serial;

public class StoryNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public StoryNotFoundException(String message){
        super(message);
    }
}
