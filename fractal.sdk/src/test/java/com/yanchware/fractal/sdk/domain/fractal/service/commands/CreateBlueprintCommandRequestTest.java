package com.yanchware.fractal.sdk.domain.fractal.service.commands;

import com.yanchware.fractal.sdk.domain.fractal.service.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateBlueprintCommandRequestTest {

  @Test
  public void createBlueprintCommandRequestValid_when_liveSystemValid() {
    var ls = TestUtils.getLiveSystemExample();
    String fractalId = "fractalId";
    var commandRequest = new CreateBlueprintCommandRequest(ls.getDescription(), true,
      BlueprintComponentDto.fromLiveSystemComponents(ls.getComponents()));
    assertThat(commandRequest.description()).contains(ls.getDescription());
    assertThat(commandRequest.isPrivate()).isTrue();
    assertThat(commandRequest.components()).isNotEmpty();
  }
}