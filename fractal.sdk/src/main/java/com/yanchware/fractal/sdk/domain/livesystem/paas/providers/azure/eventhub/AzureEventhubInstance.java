package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub;

import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSStreamingEntity;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects.RetentionDescription;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_EVENTHUB_INSTANCE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureEventhubInstance extends PaaSStreamingEntity implements LiveSystemComponent {
  private Long partitionCount;
  private Long messageRetentionInDays;
  private String userMetadata;
  private RetentionDescription retention;
  
  protected AzureEventhubInstance() {
  }

  public static class AzureEventhubInstanceBuilder extends Builder<AzureEventhubInstance, AzureEventhubInstanceBuilder> {

    @Override
    protected AzureEventhubInstance createComponent() {
      return new AzureEventhubInstance();
    }

    @Override
    protected AzureEventhubInstanceBuilder getBuilder() {
      return this;
    }

    /**
     * Specifies the partition count for the Azure Event Hub instance.
     *
     * @param partitionCount the number of partitions to be set for the Event Hub instance
     * @return the current instance of AzureEventhubInstanceBuilder with the updated partition count
     */
    public AzureEventhubInstanceBuilder withPartitionCount(Long partitionCount) {
      component.setPartitionCount(partitionCount);
      return builder;
    }

    /**
     * Specifies the message retention duration in days for the Azure Event Hub instance.
     *
     * @param messageRetentionInDays the number of days messages will be retained in the Event Hub instance
     * @return the current instance of AzureEventhubInstanceBuilder with the updated message retention duration
     */
    public AzureEventhubInstanceBuilder withMessageRetentionInDays(Long messageRetentionInDays) {
      component.setMessageRetentionInDays(messageRetentionInDays);
      return builder;
    }

    /**
     * Sets the user metadata for the Azure Event Hub instance.
     *
     * @param userMetadata the metadata string to be associated with the Azure Event Hub instance
     * @return the current instance of AzureEventhubInstanceBuilder with the updated user metadata
     */
    public AzureEventhubInstanceBuilder withUserMetadata(String userMetadata) {
      component.setUserMetadata(userMetadata);
      return builder;
    }

    /**
     * Configures the retention settings for the Azure Event Hub instance.
     *
     * @param retentionDescription an object containing the retention configuration, including time-based retention and cleanup policies
     * @return the current instance of AzureEventhubInstanceBuilder with the updated retention settings
     */
    public AzureEventhubInstanceBuilder withRetention(RetentionDescription retentionDescription) {
      component.setRetention(retentionDescription);
      return builder;
    }

    @Override
    public AzureEventhubInstance build() {
      component.setType(PAAS_EVENTHUB_INSTANCE);
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

  public static AzureEventhubInstanceBuilder builder() {
    return new AzureEventhubInstanceBuilder();
  }
}
