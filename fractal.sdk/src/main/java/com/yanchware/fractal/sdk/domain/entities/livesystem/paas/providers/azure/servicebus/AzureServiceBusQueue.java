package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSQueue;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_SERVICE_BUS_QUEUE;


@Getter
@Setter
@ToString(callSuper = true)
public class AzureServiceBusQueue extends PaaSQueue implements LiveSystemComponent {
  private Duration autoDeleteOnIdle;
  private Boolean deadLetteringOnMessageExpiration;
  private Duration defaultMessageTimeToLive;
  private Duration duplicateDetectionHistoryTimeWindow;
  private Boolean enableExpress;
  private Boolean enableBatchedOperations;
  private Boolean enablePartitioning;
  private String forwardTo;
  private String forwardDeadLetteredMessagesTo;
  private Duration lockDuration;
  private Integer maxDeliveryCount;
  private Integer maxSizeInMegabytes;
  private Long maxMessageSizeInKilobytes;
  private Boolean requiresSession;
  private Boolean requiresDuplicateDetection;

  protected AzureServiceBusQueue() {
  }

  public static class AzureServiceBusQueueBuilder extends Component.Builder<AzureServiceBusQueue, AzureServiceBusQueueBuilder> {

    @Override
    protected AzureServiceBusQueue createComponent() {
      return new AzureServiceBusQueue();
    }

    @Override
    protected AzureServiceBusQueueBuilder getBuilder() {
      return this;
    }

    public AzureServiceBusQueueBuilder withAutoDeleteOnIdle(Duration autoDeleteOnIdle) {
      component.setAutoDeleteOnIdle(autoDeleteOnIdle);
      return builder;
    }

    public AzureServiceBusQueueBuilder withDeadLetteringOnMessageExpiration(Boolean deadLetteringOnMessageExpiration) {
      component.setDeadLetteringOnMessageExpiration(deadLetteringOnMessageExpiration);
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

    public AzureServiceBusQueueBuilder withForwardTo(String forwardTo) {
      component.setForwardTo(forwardTo);
      return builder;
    }

    public AzureServiceBusQueueBuilder withForwardDeadLetteredMessagesTo(String forwardDeadLetteredMessagesTo) {
      component.setForwardDeadLetteredMessagesTo(forwardDeadLetteredMessagesTo);
      return builder;
    }

    public AzureServiceBusQueueBuilder withLockDuration(Duration lockDuration) {
      component.setLockDuration(lockDuration);
      return builder;
    }

    public AzureServiceBusQueueBuilder withMaxDeliveryCount(Integer maxDeliveryCount) {
      component.setMaxDeliveryCount(maxDeliveryCount);
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

    public AzureServiceBusQueueBuilder withRequiresSession(Boolean requiresSession) {
      component.setRequiresSession(requiresSession);
      return builder;
    }

    public AzureServiceBusQueueBuilder withRequiresDuplicateDetection(Boolean requiresDuplicateDetection) {
      component.setRequiresDuplicateDetection(requiresDuplicateDetection);
      return builder;
    }

    @Override
    public AzureServiceBusQueue build() {
      component.setType(PAAS_SERVICE_BUS_QUEUE);
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
