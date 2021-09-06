package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BlueprintComponentDto extends ComponentDto {
    private Map<String, Object> outputFields;

    public static BlueprintComponentDto fromComponent(Component component) {
        System.out.println("COMPONENT: " + component.getClass());
        ObjectMapper m = new ObjectMapper();
        Map<String, Object> mappedObject = m.convertValue(component, new TypeReference<>() {
        });
        System.out.println("MAPPPPPPP: " + mappedObject);
        return BlueprintComponentDto.builder()
                .id(component.getId().getValue())
                .displayName(component.getDisplayName())
                .description(component.getDescription())
                .type(component.getType().getId())
                .version(component.getVersion())
                .links(component.getLinks().stream().map(ComponentId::getValue).collect(Collectors.toSet()))
                .dependencies(component.getDependencies().stream().map(ComponentId::getValue).collect(Collectors.toSet()))
                .parameters(mappedObject)
                .build();
    }
}
