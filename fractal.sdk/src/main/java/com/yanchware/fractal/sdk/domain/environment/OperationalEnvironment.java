package com.yanchware.fractal.sdk.domain.environment;

public class OperationalEnvironment extends BaseEnvironment {
  public static OperationalEnvironmentBuilder builder() {
    return new OperationalEnvironmentBuilder();
  }
  
  public static class OperationalEnvironmentBuilder extends Builder<OperationalEnvironment, OperationalEnvironmentBuilder> {
    @Override
    protected OperationalEnvironment createEnvironment() {
      return new OperationalEnvironment();
    }

    @Override
    protected OperationalEnvironmentBuilder getBuilder() {
      return this;
    }
  }
  
}
