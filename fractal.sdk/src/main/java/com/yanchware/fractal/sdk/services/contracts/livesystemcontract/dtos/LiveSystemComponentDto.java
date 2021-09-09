package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Instantiating;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class LiveSystemComponentDto extends ComponentDto {
    private LiveSystemComponentStatusDto status;
    private Map<String, Object> outputFields;
    private Date lastUpdated;
    private int lastOperationRetried;
    private ProviderTypeDto provider;
    private String lastOperationStatusMessage;

    public static LiveSystemComponentDto fromLiveSystemComponent(LiveSystemComponent component) {
        Map<String, Object> allFields = ReflectionUtils.getAllFields(component);
        return LiveSystemComponentDto.builder()
                .id(((ComponentId) allFields.get("id")).getValue())
                .displayName(String.valueOf(allFields.get("displayName")))
                .description(String.valueOf(allFields.get("description")))
                .type(String.valueOf(allFields.get("liveSystemType")))
                .version("0.0.1")
                .parameters((Map<String, Object>) allFields.get("parameters"))
                .dependencies(emptySet())
                .links(emptySet())
                .outputFields(emptyMap())
                .status(Instantiating)
                .lastUpdated(new Date())
                .lastOperationRetried(999)
                .provider(null)
                .lastOperationStatusMessage("")
                .build();
    }
}
