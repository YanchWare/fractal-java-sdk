package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import com.yanchware.fractal.sdk.domain.livesystem.service.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.utils.TestUtils.getLiveSystemExample;

class InstantiateLiveSystemCommandRequestTest {

    @Test
    public void instantiateLiveSystemCommandRequestValid_when_liveSystemValid() {
        var ls = getLiveSystemExample();
        var commandRequest = new InstantiateLiveSystemCommandRequest(
                ls.getId().toString(),
                ls.getFractalId(),
                ls.getDescription(),
                null,
                ls.blueprintMapFromLiveSystemComponents(),
                ls.getEnvironment().toDto());
        
        assertLiveSystemInfo(commandRequest, ls);
        assertEnvironment(commandRequest.environment(), ls.getEnvironment());
    }

    private void assertLiveSystemInfo(InstantiateLiveSystemCommandRequest commandRequest, LiveSystemAggregate ls) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(commandRequest.liveSystemId()).contains(ls.getName(), ls.getResourceGroupId());
            softly.assertThat(commandRequest.fractalId()).contains(ls.getName(), ls.getResourceGroupId(), DEFAULT_VERSION);
            softly.assertThat(commandRequest.description()).isEqualTo(ls.getDescription());
        });
    }

    private void assertEnvironment(EnvironmentDto envDto, EnvironmentAggregate env) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(envDto.id().type().toString()).isEqualTo(env.getEnvironmentType().toString());
            softly.assertThat(envDto.id().ownerId()).isEqualTo(env.getOwnerId());
            softly.assertThat(envDto.id().shortName()).isEqualTo(env.getShortName());
            softly.assertThat(envDto.parameters()).isEqualTo(env.toDto().parameters());
        });
    }

}