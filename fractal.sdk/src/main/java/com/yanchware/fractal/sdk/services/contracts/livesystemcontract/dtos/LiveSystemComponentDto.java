package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Instantiating;
import static java.util.Collections.emptyMap;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
public class LiveSystemComponentDto extends ComponentDto {
    private LiveSystemComponentStatusDto status;
    private Map<String, Object> outputFields;
    private Date lastUpdated;
    private ProviderType provider;

    public static Map<String, LiveSystemComponentDto> fromLiveSystemComponents(Collection<LiveSystemComponent> lsComponents) {
        Map<String, LiveSystemComponentDto> map = new HashMap<>();
        for(LiveSystemComponent comp : lsComponents) {
            List<Map<String, Object>> listOfComponents = ReflectionUtils.buildComponents(comp);
            for(var component : listOfComponents) {
                LiveSystemComponentDto componentDto = toLiveSystemComponent(comp, component);
                map.put(componentDto.getId(), componentDto);
            }
        }
        return map;
    }

    private static LiveSystemComponentDto toLiveSystemComponent(LiveSystemComponent component, Map<String, Object> allFields) {
        return LiveSystemComponentDto.builder()
                .id(((ComponentId) allFields.get(ID_KEY)).getValue())
                .displayName(String.valueOf(allFields.get(DISPLAY_NAME_KEY)))
                .description(String.valueOf(allFields.get(DESCRIPTION_KEY)))
                .type(String.valueOf(allFields.get(LIVESYSTEM_TYPE)))
                .version(String.valueOf(allFields.get(VERSION_KEY)))
                .parameters((Map<String, Object>) allFields.get(PARAMETERS_KEY))
                .dependencies((Set<String>) allFields.get(DEPENDENCIES_KEY))
                .links((Set<ComponentLink>) allFields.get(LINKS_KEY))
                .outputFields(emptyMap())
                .status(Instantiating)
                .lastUpdated(new Date())
                .provider(component.getProvider())
                .build();
    }
}
