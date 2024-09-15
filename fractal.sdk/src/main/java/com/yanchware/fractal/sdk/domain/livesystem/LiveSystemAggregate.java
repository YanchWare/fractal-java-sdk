package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.service.LiveSystemService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.*;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.COMPONENT_TYPE;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class LiveSystemAggregate implements Validatable {
    private final static String ID_IS_NULL = "[LiveSystem Validation] Id has not been defined and it is required";
    private final static String NAME_IS_NULL = "[LiveSystem Validation] Name has not been defined and it is required";
    private final static String RESOURCE_GROUP_ID_IS_NULL = "[LiveSystem Validation] ResourceGroupId has not been defined and it is required'";
    private final static String EMPTY_COMPONENT_LIST = "[LiveSystem Validation] Components list is null or empty and at least one component is required";
    private final LiveSystemService service;

    @Getter
    private LiveSystemIdValue id;
    @Getter
    private FractalIdValue fractalId;
    @Getter
    private String description;
    @Getter
    private EnvironmentAggregate environment;
    @Getter
    private Date created;
    @Getter
    private Date lastUpdated;
    @Getter
    private Collection<LiveSystemComponent> components;
    @Getter
    private ProviderType provider;

    protected LiveSystemAggregate(
            HttpClient client,
            SdkConfiguration sdkConfiguration,
            RetryRegistry retryRegistry)
    {
        service = new LiveSystemService(client, sdkConfiguration, retryRegistry);
        components = new ArrayList<>();
    }

    // TODO: Use entity instead of LiveSystemComponentMutationDto
    public LiveSystemComponentMutationDto instantiateComponent(String componentId) throws InstantiatorException {
        return service.instantiateComponent(id, componentId);
    }

    public LiveSystemComponentMutationDto getComponentMutationStatus(String componentId, String mutationId)
            throws InstantiatorException
    {
        return service.getComponentMutationStatus(getId().toString(), componentId, mutationId);
    }

    // TODO: Use entity instead of LiveSystemMutationDto
    public LiveSystemMutationDto instantiate() throws InstantiatorException {
        log.info("Starting to instantiate live system: {}", getId());

        if (components == null || components.isEmpty()) {
            throw new InstantiatorException(EMPTY_COMPONENT_LIST);
        }

        if (service.retrieveLiveSystem(getId()) != null) {
            return service.updateLiveSystem(
                    getId().toString(),
                    fractalId,
                    description,
                    provider.toString(),
                    blueprintMapFromLiveSystemComponents(),
                    environment.toDto());
        }

        return service.instantiateLiveSystem(
                getId().toString(),
                fractalId,
                description,
                provider.toString(),
                blueprintMapFromLiveSystemComponents(),
                environment.toDto());
    }

    public void checkLiveSystemMutationStatus(String liveSystemMutationId) throws InstantiatorException {
        service.checkLiveSystemMutationStatus(getId(), liveSystemMutationId);
    }

    protected Map<String, LiveSystemComponentDto> blueprintMapFromLiveSystemComponents() {
        Map<String, LiveSystemComponentDto> map = new HashMap<>();
        for (LiveSystemComponent comp : getComponents()) {
            List<Map<String, Object>> listOfComponents = ReflectionUtils.buildComponents(comp);
            for (var component : listOfComponents) {
                LiveSystemComponentDto componentDto = toLiveSystemComponentDto(comp, component);
                map.put(componentDto.getId(), componentDto);
            }
        }
        return map;
    }

    private static LiveSystemComponentDto toLiveSystemComponentDto(LiveSystemComponent component, Map<String, Object> allFields) {
        return LiveSystemComponentDto.builder()
                .withFields(allFields)
                .withType(String.valueOf(allFields.get(COMPONENT_TYPE)))
                .withStatus(LiveSystemComponentStatusDto.Instantiating)
                .withOutputFields(emptyMap())
                .withLastUpdated(new Date())
                .withProvider(component.getProvider())
                .build();
    }


    @Override
    public Collection<String> validate() {
        Collection<String> errors = new ArrayList<>();

        if (id == null) {
            errors.add(ID_IS_NULL);
            return errors;
        }

        if (isBlank(id.name())) {
            errors.add(NAME_IS_NULL);
        }

        if (isBlank(id.resourceGroupId())) {
            errors.add(RESOURCE_GROUP_ID_IS_NULL);
        }

        return errors;
    }
}