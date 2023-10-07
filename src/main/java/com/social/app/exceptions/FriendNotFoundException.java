package com.social.app.exceptions;

import java.io.Serial;

public class FriendNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public FriendNotFoundException(String message){
        super(message);
    }
}
