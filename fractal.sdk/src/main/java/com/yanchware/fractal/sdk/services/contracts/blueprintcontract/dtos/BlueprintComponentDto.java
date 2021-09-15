package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.BLUEPRINT_TYPE;
import static java.util.Collections.emptySet;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Slf4j
public class BlueprintComponentDto extends ComponentDto {
    private Set<String> outputFields;

    public static List<BlueprintComponentDto> fromLiveSystemComponents(Collection<LiveSystemComponent> lsComponents) {
        List<BlueprintComponentDto> blueprintComponentDtoList = new ArrayList<>();
        for(LiveSystemComponent comp : lsComponents) {
            List<Map<String, Object>> listOfComponents = ReflectionUtils.buildComponents(comp);
            for(var component : listOfComponents) {
                BlueprintComponentDto componentDto = toBlueprintComponentDto(component);
                blueprintComponentDtoList.add(componentDto);
            }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            log.debug("Json Map: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(blueprintComponentDtoList));
        } catch (JsonProcessingException e) {
            log.error("Error when trying to process component: {}", lsComponents, e);
        }
        return blueprintComponentDtoList;
    }

    private static BlueprintComponentDto toBlueprintComponentDto(Map<String, Object> allFields) {
        String liveSystemId = ((ComponentId) allFields.get("id")).getValue();
        return BlueprintComponentDto.builder()
                .id(liveSystemId)
                .displayName(String.valueOf(allFields.get("displayName")))
                .description(String.format("Blueprint created via SDK by LiveSystem with ID: %s", liveSystemId))
                .type(String.valueOf(allFields.get(BLUEPRINT_TYPE)))
                .version(String.valueOf(allFields.get("version")))
                .parameters((Map<String, Object>) allFields.get("parameters"))
                .dependencies((Set<String>) allFields.get("dependencies"))
                .links((Set<String>) allFields.get("links"))
                .outputFields(emptySet())
                .build();
    }
}
