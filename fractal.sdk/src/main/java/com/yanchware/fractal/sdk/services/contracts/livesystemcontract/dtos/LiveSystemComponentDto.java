package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.COMPONENT_TYPE;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Instantiating;
import static java.util.Collections.emptyMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class LiveSystemComponentDto extends ComponentDto {
    private LiveSystemComponentStatusDto status;
    private Map<String, Object> outputFields;
    private Date lastUpdated;
    private ProviderType provider;

    public static LiveSystemComponentDtoBuilder builder() {
        return new LiveSystemComponentDtoBuilder();
    }

    public static class LiveSystemComponentDtoBuilder extends Builder<LiveSystemComponentDto, LiveSystemComponentDtoBuilder> {

        @Override
        protected LiveSystemComponentDto createComponent() {
            return new LiveSystemComponentDto();
        }

        @Override
        protected LiveSystemComponentDtoBuilder getBuilder() {
            return this;
        }

        public LiveSystemComponentDtoBuilder withStatus(LiveSystemComponentStatusDto status) {
            componentDto.setStatus(status);
            return builder;
        }

        public LiveSystemComponentDtoBuilder withOutputFields(Map<String, Object> outputFields) {
            if (componentDto.getOutputFields() == null) {
                componentDto.setOutputFields(new HashMap<>());
            }

            componentDto.getOutputFields().putAll(outputFields);

            return builder;
        }

        public LiveSystemComponentDtoBuilder withLastUpdated(Date lastUpdated) {
            componentDto.setLastUpdated(lastUpdated);
            return builder;
        }

        public LiveSystemComponentDtoBuilder withProvider(ProviderType provider) {
            componentDto.setProvider(provider);
            return builder;
        }
    }

    public static Map<String, LiveSystemComponentDto> fromLiveSystemComponents(Collection<LiveSystemComponent> lsComponents) {
        Map<String, LiveSystemComponentDto> map = new HashMap<>();
        for (LiveSystemComponent comp : lsComponents) {
            List<Map<String, Object>> listOfComponents = ReflectionUtils.buildComponents(comp);
            for (var component : listOfComponents) {
                LiveSystemComponentDto componentDto = toLiveSystemComponent(comp, component);
                map.put(componentDto.getId(), componentDto);
            }
        }
        return map;
    }

    private static LiveSystemComponentDto toLiveSystemComponent(LiveSystemComponent component, Map<String, Object> allFields) {
        return LiveSystemComponentDto.builder()
                .withFields(allFields)
                .withType(String.valueOf(allFields.get(COMPONENT_TYPE)))
                .withStatus(Instantiating)
                .withOutputFields(emptyMap())
                .withLastUpdated(new Date())
                .withProvider(component.getProvider())
                .build();
    }
}
