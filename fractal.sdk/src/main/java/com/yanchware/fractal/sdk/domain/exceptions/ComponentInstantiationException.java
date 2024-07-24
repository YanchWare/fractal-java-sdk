package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;

public class ComponentInstantiationException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    

    public ComponentInstantiationException(String message) {
        super(message);
    }

    public ComponentInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
