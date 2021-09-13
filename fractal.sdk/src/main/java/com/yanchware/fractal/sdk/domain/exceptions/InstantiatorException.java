package com.yanchware.fractal.sdk.domain.exceptions;

public class InstantiatorException extends Exception {
    private static final long serialVersionUID = 1L;

    public InstantiatorException(String message) {
        super(message);
    }

    public InstantiatorException(String message, Throwable cause) {
        super(message, cause);
    }

}
