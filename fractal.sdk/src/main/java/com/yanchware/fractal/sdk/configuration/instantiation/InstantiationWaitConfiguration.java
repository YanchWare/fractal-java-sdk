package com.yanchware.fractal.sdk.configuration.instantiation;

import lombok.Data;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_LIVE_SYSTEM_INSTANTIATION_WAIT_TIMEOUT_MINUTES;

@Data
public class InstantiationWaitConfiguration
{

  /**
   * Wait for the instantiation, instead of terminate the execution right after the instantiation.
   */
  public boolean waitForInstantiation;

  /**
   * Wait for the instantiation until the timeout (Default is 120 minutes).
   */
  public int timeoutMinutes = DEFAULT_LIVE_SYSTEM_INSTANTIATION_WAIT_TIMEOUT_MINUTES;

  /**
   * Decide whether immediately terminate the wait process when at least a component has failed (Default is true).
   * If true: it waits for a single component to fail to terminate the wait process
   * If false: it waits for all the components to be successfully instantiated or failed before terminiating the wait process.
   */
  public boolean failFast;
}
