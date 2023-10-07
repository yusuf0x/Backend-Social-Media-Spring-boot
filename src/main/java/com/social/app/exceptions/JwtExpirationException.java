package com.social.app.exceptions;

import java.io.Serial;

public class JwtExpirationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public JwtExpirationException(String message) {
        super(message);
    }
}