package com.social.app.exceptions;

import java.io.Serial;

public class NotificationNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public NotificationNotFoundException(String message){
        super(message);
    }
}
