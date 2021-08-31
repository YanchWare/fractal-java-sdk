package com.yanchware.fractal.sdk.services.livesystemcontract.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class LiveSystemComponentDto extends ComponentDto {
    private LiveSystemComponentStatusDto status;
    private Map<String, Object> outputFields;
    private Date lastUpdated;
    private int lastOperationRetried;
    private ProviderTypeDto provider;
    private String lastOperationStatusMessage;
}
