package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class LiveSystemComponentDto extends ComponentDto {
    private LiveSystemComponentStatusDto status;
    private Map<String, Object> outputFields;
    private Date lastUpdated;
    private int lastOperationRetried;
    private ProviderTypeDto provider;
    private String lastOperationStatusMessage;
}
