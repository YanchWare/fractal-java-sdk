package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateBlueprintCommandRequestTest {

    @Test
    public void createBlueprintCommandRequestValid_when_liveSystemValid() {
        var ls = TestUtils.getLiveSystemExample();
        String fractalId = "fractalId";
        CreateBlueprintCommandRequest commandRequest = CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents(), fractalId);
        TestUtils.printJsonRepresentation(commandRequest);
        assertThat(commandRequest.getDescription()).contains("Blueprint created via SDK from LiveSystem with Fractal ID", fractalId);
        assertThat(commandRequest.isPrivate()).isTrue();
        assertThat(commandRequest.getComponents()).isNotEmpty();
    }
}