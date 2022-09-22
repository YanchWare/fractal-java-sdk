package com.yanchware.fractal.sdk.domain.exceptions;

import com.yanchware.fractal.sdk.services.contracts.providerscontract.dtos.ProviderLiveSystemComponentDto;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ProviderException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<ProviderLiveSystemComponentDto> failedComponents = new ArrayList<>();

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderException(List<ProviderLiveSystemComponentDto> failedComponents, String message) {
        super(message);
        this.failedComponents = failedComponents;
    }

}
