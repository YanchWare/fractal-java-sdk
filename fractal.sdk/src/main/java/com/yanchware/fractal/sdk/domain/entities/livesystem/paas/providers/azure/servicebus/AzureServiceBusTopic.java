package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSMessageEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_SERVICE_BUS_TOPIC;


@Getter
@Setter
@ToString(callSuper = true)
public class AzureServiceBusTopic extends PaaSMessageEntity implements LiveSystemComponent {
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

    /**
     * Idle after which the topic is automatically deleted
     * @param autoDeleteOnIdle
     */
    public AzureServiceBusQueueBuilder withAutoDeleteOnIdle(Duration autoDeleteOnIdle) {
      component.setAutoDeleteOnIdle(autoDeleteOnIdle);
      return builder;
    }

    /**
     * Duration after which the message expires
     * @param defaultMessageTimeToLive
     */
    public AzureServiceBusQueueBuilder withDefaultMessageTimeToLive(Duration defaultMessageTimeToLive) {
      component.setDefaultMessageTimeToLive(defaultMessageTimeToLive);
      return builder;
    }

    /**
     * Duration of duplicate detection history
     * @param duplicateDetectionHistoryTimeWindow
     */
    public AzureServiceBusQueueBuilder withDuplicateDetectionHistoryTimeWindow(Duration duplicateDetectionHistoryTimeWindow) {
      component.setDuplicateDetectionHistoryTimeWindow(duplicateDetectionHistoryTimeWindow);
      return builder;
    }

    /**
     * Indicates if Express Entities are enabled
     * @param enableExpress
     */
    public AzureServiceBusQueueBuilder withEnableExpress(Boolean enableExpress) {
      component.setEnableExpress(enableExpress);
      return builder;
    }

    /**
     * Indicates if batched operations are enabled
     * @param enableBatchedOperations
     */
    public AzureServiceBusQueueBuilder withEnableBatchedOperations(Boolean enableBatchedOperations) {
      component.setEnableBatchedOperations(enableBatchedOperations);
      return builder;
    }

    /**
     * Indicates if the topic will be partitioned across multiple message brokers
     * @param enablePartitioning
     */
    public AzureServiceBusQueueBuilder withEnablePartitioning(Boolean enablePartitioning) {
      component.setEnablePartitioning(enablePartitioning);
      return builder;
    }

    /**
     * Maximum size of the topic in megabytes
     * @param maxSizeInMegabytes
     */
    public AzureServiceBusQueueBuilder withMaxSizeInMegabytes(Integer maxSizeInMegabytes) {
      component.setMaxSizeInMegabytes(maxSizeInMegabytes);
      return builder;
    }

    /**
     * Maximum size of message payload in kilobytes
     * @param maxMessageSizeInKilobytes
     */
    public AzureServiceBusQueueBuilder withMaxMessageSizeInKilobytes(Long maxMessageSizeInKilobytes) {
      component.setMaxMessageSizeInKilobytes(maxMessageSizeInKilobytes);
      return builder;
    }

    /**
     * Indicates if the topic requires duplicate detection
     * @param requiresDuplicateDetection
     */
    public AzureServiceBusQueueBuilder withRequiresDuplicateDetection(Boolean requiresDuplicateDetection) {
      component.setRequiresDuplicateDetection(requiresDuplicateDetection);
      return builder;
    }

    /**
     * Indicates if the topic supports ordering
     * @param supportOrdering
     */
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
