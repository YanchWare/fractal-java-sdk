package com.yanchware.fractal.sdk.configuration.instantiation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_LIVE_SYSTEM_INSTANTIATION_WAIT_TIMEOUT_MINUTES;

@Getter
@Setter(AccessLevel.PRIVATE)
public class InstantiationWaitConfiguration {
  public boolean waitForInstantiation;

  public Integer timeoutMinutes;

  public static InstantiationWaitConfigurationBuilder builder() {
    return new InstantiationWaitConfigurationBuilder();
  }

  public static class InstantiationWaitConfigurationBuilder {
    private final InstantiationWaitConfiguration configuration;
    private final InstantiationWaitConfigurationBuilder builder;

    public InstantiationWaitConfigurationBuilder() {
      this.configuration = new InstantiationWaitConfiguration();
      this.builder = this;
    }

    /**
     * Wait for the instantiation, instead of terminate the execution right after the instantiation.
     */
    public InstantiationWaitConfigurationBuilder withWaitForInstantiation(boolean waitForInstantiation) {
      configuration.setWaitForInstantiation(waitForInstantiation);
      return builder;
    }

    /**
     * Wait for the instantiation until the timeout (Default is 120 minutes).
     */
    public InstantiationWaitConfigurationBuilder withTimeoutMinutes(int timeoutMinutes) {
      configuration.setTimeoutMinutes(timeoutMinutes);
      return builder;
    }


    public InstantiationWaitConfiguration build() {

      if (configuration.waitForInstantiation
          && configuration.timeoutMinutes == null) {
        configuration.setTimeoutMinutes(DEFAULT_LIVE_SYSTEM_INSTANTIATION_WAIT_TIMEOUT_MINUTES);
      }

      return configuration;
    }
  }
}
