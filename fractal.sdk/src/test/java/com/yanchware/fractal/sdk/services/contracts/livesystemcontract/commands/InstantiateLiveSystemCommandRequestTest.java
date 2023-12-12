package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.utils.TestUtils.getLiveSystemExample;

class InstantiateLiveSystemCommandRequestTest {

    @Test
    public void instantiateLiveSystemCommandRequestValid_when_liveSystemValid() {
        var ls = getLiveSystemExample();
        var commandRequest = InstantiateLiveSystemCommandRequest.fromLiveSystem(ls);
        
        assertLiveSystemInfo(commandRequest, ls);
        assertEnvironment(commandRequest.getEnvironment(), ls.getEnvironment());
    }

    private void assertLiveSystemInfo(InstantiateLiveSystemCommandRequest commandRequest, LiveSystem ls) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(commandRequest.getLiveSystemId()).contains(ls.getName(), ls.getResourceGroupId());
            softly.assertThat(commandRequest.getFractalId()).contains(ls.getName(), ls.getResourceGroupId(), DEFAULT_VERSION);
            softly.assertThat(commandRequest.getDescription()).isEqualTo(ls.getDescription());
        });
    }

    private void assertEnvironment(EnvironmentDto envDto, Environment env) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(envDto.getId().getType()).isEqualTo(env.getEnvironmentType());
            softly.assertThat(envDto.getId().getOwnerId()).isEqualTo(env.getOwnerId());
            softly.assertThat(envDto.getId().getShortName()).isEqualTo(env.getShortName());
            softly.assertThat(envDto.getParameters()).isEqualTo(env.getParameters());
        });
    }

}