package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

public class EnvironmentInitializationException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private Collection<String> failedSteps = new ArrayList<>();

    public EnvironmentInitializationException(String message) {
        super(message);
    }

    public EnvironmentInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvironmentInitializationException(Collection<String> failedSteps, String message) {
        super(message);
        this.failedSteps = failedSteps;
    }

}
