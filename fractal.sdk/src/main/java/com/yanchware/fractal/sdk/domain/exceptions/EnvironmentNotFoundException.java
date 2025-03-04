package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;

public class EnvironmentNotFoundException extends InstantiatorException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EnvironmentNotFoundException(String message) {
        super(message);
    }

    public EnvironmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
