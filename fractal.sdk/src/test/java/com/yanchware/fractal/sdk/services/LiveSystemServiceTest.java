package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import org.junit.Test;

public class LiveSystemServiceTest {

    private LiveSystemService liveSystemService = new LiveSystemService(null, null);

    @Test
    public void test() throws InstantiatorException {
        InstantiateLiveSystemCommandRequest command = InstantiateLiveSystemCommandRequest.builder()
                .liveSystemId("resourceGroupId/prod") //form of "resourceGroupId/name"
                .description("prod")
                .fractalId("resourceGroupId/fractalName:fractalVersion") //form of "resourceGroupId/fractalName:fractalVersion", needs to exist in blueprints, otherwise wont work
                .type("type")
                .provider("azure") //can be an enum, as in ls service it will be converted to enum.
                .environmentDto(getEnvironment())
                .blueprintMap(null) // needs to have at least one component, needs to have same components as blueprint in blueprint svc and all providers of component valid
                .build();

        liveSystemService.instantiate(command);
    }

    private EnvironmentDto getEnvironment() {
        return EnvironmentDto.builder()
                .id("prod")
                .parentId("parent-id")
                .parentType("folder")
                .displayName("PROD")
                .build();
    }

}