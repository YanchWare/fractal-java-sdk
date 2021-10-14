package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.utils.TestUtils.getLiveSystemExample;
import static org.assertj.core.api.Assertions.assertThat;

class InstantiateLiveSystemCommandRequestTest {

    @Test
    public void instantiateLiveSystemCommandRequestValid_when_liveSystemValid() {
        var instantiateLiveSystemCommandRequest = InstantiateLiveSystemCommandRequest.fromLiveSystem(getLiveSystemExample());
        assertThat(instantiateLiveSystemCommandRequest.getBlueprintMap()).hasSize(4);
        System.out.println(instantiateLiveSystemCommandRequest);
    }

}