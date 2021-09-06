package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import lombok.Data;

import java.util.Map;

@Data
public class BlueprintComponentDto extends ComponentDto {
    private Map<String, Object> outputFields;
}
