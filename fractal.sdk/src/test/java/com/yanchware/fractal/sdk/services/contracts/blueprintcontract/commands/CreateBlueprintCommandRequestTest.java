package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateBlueprintCommandRequestTest {

    @Test
    public void createBlueprintCommandRequestValid_when_liveSystemValid() {
        var ls = TestUtils.getLiveSystemExample();
        String blueprintDescription = "blueprint description";
        CreateBlueprintCommandRequest commandRequest = CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents(), blueprintDescription);
        assertThat(commandRequest.getDescription()).isEqualTo(blueprintDescription);
        assertThat(commandRequest.isPrivate()).isTrue();
        assertThat(commandRequest.getComponents()).hasSize(4);
    }
}