package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSMessageEntity;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_SERVICE_BUS_QUEUE;


@Getter
@Setter
@ToString(callSuper = true)
public class AzureServiceBusQueue extends PaaSMessageEntity implements LiveSystemComponent {
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

  public static AzureServiceBusQueueBuilder builder() {
    return new AzureServiceBusQueueBuilder();
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

  public static class AzureServiceBusQueueBuilder extends Component.Builder<AzureServiceBusQueue,
    AzureServiceBusQueueBuilder> {

    @Override
    protected AzureServiceBusQueue createComponent() {
      return new AzureServiceBusQueue();
    }

    @Override
    protected AzureServiceBusQueueBuilder getBuilder() {
      return this;
    }

    /**
     * Idle after which the queue is automatically deleted
     *
     * @param autoDeleteOnIdle
     */
    public AzureServiceBusQueueBuilder withAutoDeleteOnIdle(Duration autoDeleteOnIdle) {
      component.setAutoDeleteOnIdle(autoDeleteOnIdle);
      return builder;
    }

    /**
     * If enabled, dead letter support is available when a message expires
     *
     * @param deadLetteringOnMessageExpiration
     */
    public AzureServiceBusQueueBuilder withDeadLetteringOnMessageExpiration(Boolean deadLetteringOnMessageExpiration) {
      component.setDeadLetteringOnMessageExpiration(deadLetteringOnMessageExpiration);
      return builder;
    }

    /**
     * Duration after which the message expires
     *
     * @param defaultMessageTimeToLive
     */
    public AzureServiceBusQueueBuilder withDefaultMessageTimeToLive(Duration defaultMessageTimeToLive) {
      component.setDefaultMessageTimeToLive(defaultMessageTimeToLive);
      return builder;
    }

    /**
     * Duration of duplicate detection history
     *
     * @param duplicateDetectionHistoryTimeWindow
     */
    public AzureServiceBusQueueBuilder withDuplicateDetectionHistoryTimeWindow(Duration duplicateDetectionHistoryTimeWindow) {
      component.setDuplicateDetectionHistoryTimeWindow(duplicateDetectionHistoryTimeWindow);
      return builder;
    }

    /**
     * Indicates if Express Entities are enabled
     *
     * @param enableExpress
     */
    public AzureServiceBusQueueBuilder withEnableExpress(Boolean enableExpress) {
      component.setEnableExpress(enableExpress);
      return builder;
    }

    /**
     * Indicates if batched operations are enabled
     *
     * @param enableBatchedOperations
     */
    public AzureServiceBusQueueBuilder withEnableBatchedOperations(Boolean enableBatchedOperations) {
      component.setEnableBatchedOperations(enableBatchedOperations);
      return builder;
    }

    /**
     * Indicates if the queue will be partitioned across multiple message brokers
     *
     * @param enablePartitioning
     */
    public AzureServiceBusQueueBuilder withEnablePartitioning(Boolean enablePartitioning) {
      component.setEnablePartitioning(enablePartitioning);
      return builder;
    }

    /**
     * Name of the queue where to forward messages
     *
     * @param forwardTo
     */
    public AzureServiceBusQueueBuilder withForwardTo(String forwardTo) {
      component.setForwardTo(forwardTo);
      return builder;
    }

    /**
     * Name of the queue where to forward dead letter messages
     *
     * @param forwardDeadLetteredMessagesTo
     */
    public AzureServiceBusQueueBuilder withForwardDeadLetteredMessagesTo(String forwardDeadLetteredMessagesTo) {
      component.setForwardDeadLetteredMessagesTo(forwardDeadLetteredMessagesTo);
      return builder;
    }

    /**
     * Duration of a peek-lock (amount of time that a message is locked for other receivers)
     *
     * @param lockDuration
     */
    public AzureServiceBusQueueBuilder withLockDuration(Duration lockDuration) {
      component.setLockDuration(lockDuration);
      return builder;
    }

    /**
     * Maximum delivery count
     *
     * @param maxDeliveryCount
     */
    public AzureServiceBusQueueBuilder withMaxDeliveryCount(Integer maxDeliveryCount) {
      component.setMaxDeliveryCount(maxDeliveryCount);
      return builder;
    }

    /**
     * Maximum size of the queue in megabytes
     *
     * @param maxSizeInMegabytes
     */
    public AzureServiceBusQueueBuilder withMaxSizeInMegabytes(Integer maxSizeInMegabytes) {
      component.setMaxSizeInMegabytes(maxSizeInMegabytes);
      return builder;
    }

    /**
     * Maximum size of message payload in kilobytes
     *
     * @param maxMessageSizeInKilobytes
     */
    public AzureServiceBusQueueBuilder withMaxMessageSizeInKilobytes(Long maxMessageSizeInKilobytes) {
      component.setMaxMessageSizeInKilobytes(maxMessageSizeInKilobytes);
      return builder;
    }

    /**
     * Indicates if the queue supports the concept of sessions
     *
     * @param requiresSession
     */
    public AzureServiceBusQueueBuilder withRequiresSession(Boolean requiresSession) {
      component.setRequiresSession(requiresSession);
      return builder;
    }

    /**
     * Indicates if the queue requires duplicate detection
     *
     * @param requiresDuplicateDetection
     */
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
}
