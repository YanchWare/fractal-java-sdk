package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSTopic;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_SERVICE_BUS_TOPIC;


@Getter
@Setter
@ToString(callSuper = true)
public class AzureServiceBusTopic extends PaaSTopic implements LiveSystemComponent {
  private Duration autoDeleteOnIdle;
  private Duration defaultMessageTimeToLive;
  private Duration duplicateDetectionHistoryTimeWindow;
  private Boolean enableExpress;
  private Boolean enableBatchedOperations;
  private Boolean enablePartitioning;
  private Integer maxSizeInMegabytes;
  private Long maxMessageSizeInKilobytes;
  private Boolean requiresDuplicateDetection;
  private Boolean supportOrdering;

  protected AzureServiceBusTopic() {
  }

  public static class AzureServiceBusQueueBuilder extends Builder<AzureServiceBusTopic, AzureServiceBusQueueBuilder> {

    @Override
    protected AzureServiceBusTopic createComponent() {
      return new AzureServiceBusTopic();
    }

    @Override
    protected AzureServiceBusQueueBuilder getBuilder() {
      return this;
    }

    public AzureServiceBusQueueBuilder withAutoDeleteOnIdle(Duration autoDeleteOnIdle) {
      component.setAutoDeleteOnIdle(autoDeleteOnIdle);
      return builder;
    }

    public AzureServiceBusQueueBuilder withDefaultMessageTimeToLive(Duration defaultMessageTimeToLive) {
      component.setDefaultMessageTimeToLive(defaultMessageTimeToLive);
      return builder;
    }

    public AzureServiceBusQueueBuilder withDuplicateDetectionHistoryTimeWindow(Duration duplicateDetectionHistoryTimeWindow) {
      component.setDuplicateDetectionHistoryTimeWindow(duplicateDetectionHistoryTimeWindow);
      return builder;
    }

    public AzureServiceBusQueueBuilder withEnableExpress(Boolean enableExpress) {
      component.setEnableExpress(enableExpress);
      return builder;
    }

    public AzureServiceBusQueueBuilder withEnableBatchedOperations(Boolean enableBatchedOperations) {
      component.setEnableBatchedOperations(enableBatchedOperations);
      return builder;
    }

    public AzureServiceBusQueueBuilder withEnablePartitioning(Boolean enablePartitioning) {
      component.setEnablePartitioning(enablePartitioning);
      return builder;
    }

    public AzureServiceBusQueueBuilder withMaxSizeInMegabytes(Integer maxSizeInMegabytes) {
      component.setMaxSizeInMegabytes(maxSizeInMegabytes);
      return builder;
    }

    public AzureServiceBusQueueBuilder withMaxMessageSizeInKilobytes(Long maxMessageSizeInKilobytes) {
      component.setMaxMessageSizeInKilobytes(maxMessageSizeInKilobytes);
      return builder;
    }

    public AzureServiceBusQueueBuilder withRequiresDuplicateDetection(Boolean requiresDuplicateDetection) {
      component.setRequiresDuplicateDetection(requiresDuplicateDetection);
      return builder;
    }

    public AzureServiceBusQueueBuilder withSupportOrdering(Boolean supportOrdering) {
      component.setSupportOrdering(supportOrdering);
      return builder;
    }

    @Override
    public AzureServiceBusTopic build() {
      component.setType(PAAS_SERVICE_BUS_TOPIC);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    return errors;
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureServiceBusQueueBuilder builder() {
    return new AzureServiceBusQueueBuilder();
  }
}
