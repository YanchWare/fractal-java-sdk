package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;

public class EnvironmentException extends InstantiatorException {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public EnvironmentException(String message) {
        super(message);
    }

    public EnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
