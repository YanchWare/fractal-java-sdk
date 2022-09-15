package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;

public class ProviderException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }

}
