package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateBlueprintCommandRequestTest {

    @Test
    public void createBlueprintCommandRequestValid_when_liveSystemValid() {
        var aks = TestUtils.getAksExample();
        String blueprintDescription = "blueprint description";
        CreateBlueprintCommandRequest commandRequest = CreateBlueprintCommandRequest.fromLiveSystem(List.of(aks), blueprintDescription);
        assertThat(commandRequest.getDescription()).isEqualTo(blueprintDescription);
        assertThat(commandRequest.isPrivate()).isTrue();
        assertThat(commandRequest.getComponents()).hasSize(1);
    }
}