package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class ManagementEnvironment extends BaseEnvironment {
  private final static String OWNER_ID_IS_WRONG = "Operational environment must have the same owner id as management environment.";
  private final static String ENVIRONMENT_TYPE_ID_IS_WRONG = "Operational environment must have the same type as management environment.\"";
  
  private final List<OperationalEnvironment> operationalEnvironments;
  
  public ManagementEnvironment() {
    operationalEnvironments = new ArrayList<>();
  }

  public static ManagementEnvironmentBuilder builder() {
    return new ManagementEnvironmentBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    
    if(!operationalEnvironments.isEmpty()) {
      for (OperationalEnvironment operationalEnvironment : operationalEnvironments) {
        if(!operationalEnvironment.getId().ownerId().equals(this.getId().ownerId())) {
          errors.add(OWNER_ID_IS_WRONG);
        }
        
        if(operationalEnvironment.getId().type() != this.getId().type()) {
          errors.add(ENVIRONMENT_TYPE_ID_IS_WRONG);
        }
      }
    }
    
    return errors;
  }

  public static class ManagementEnvironmentBuilder extends Builder<ManagementEnvironment, ManagementEnvironmentBuilder> { // Specify Builder type

    public ManagementEnvironmentBuilder withOperationalEnvironment(OperationalEnvironment operationalEnvironment) {
      return withOperationalEnvironments(List.of(operationalEnvironment));
    }

    public ManagementEnvironmentBuilder withOperationalEnvironments(Collection<OperationalEnvironment> operationalEnvironments) {
      if (CollectionUtils.isBlank(operationalEnvironments)) {
        return this;
      }

      environment.getOperationalEnvironments().addAll(operationalEnvironments);
      return this;
    }
    
    @Override
    protected ManagementEnvironment createEnvironment() {
      return new ManagementEnvironment();
    }

    @Override
    protected ManagementEnvironmentBuilder getBuilder() {
      return this;
    }
  }
}
