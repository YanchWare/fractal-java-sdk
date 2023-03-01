package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzurePricingPlan;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureAppServicePlanTest {
  private final static String APP_SERVICE_PLAN_NAME = "name-test";
  private final static AzureRegion SELECTED_REGION = AzureRegion.US_EAST;
  private final static AzureOsType SELECTED_OPERATING_SYSTEM = AzureOsType.LINUX;
  private final static AzurePricingPlan SELECTED_PRICING_PLAN = AzurePricingPlan.BASIC_B1;
  private final static AzureResourceGroup AZURE_RESOURCE_GROUP = AzureResourceGroup.builder()
      .withName("new-resource-group")
      .withRegion(SELECTED_REGION)
      .build();

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNullName() {
    assertThatThrownBy(() -> AzureAppServicePlan.builder().withName(null).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithEmptyName() {
    assertThatThrownBy(() -> AzureAppServicePlan.builder().withName("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithToLongName() {
    var veryLongName = "aTfR2EbiZzWwaCLdGWy2pCujyFCbFzLgCkYQeU9zF";
    assertThatThrownBy(() -> AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNameThatStartsWithHyphen() {
    var veryLongName = "-name";
    assertThatThrownBy(() -> AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNameThatEndsWithHyphen() {
    var veryLongName = "name-";
    assertThatThrownBy(() -> AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNameThatContainsUnderscore() {
    var veryLongName = "name_test";
    assertThatThrownBy(() -> AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperName() {
    var appServicePlanName = "name-test";
    var appServicePlan = getAppServicePlanBuilder()
        .withName(appServicePlanName)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameAndResourceGroup() {
    var appServicePlan = getAppServicePlanBuilder()
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupAndRegion() {


    var appServicePlan = getAppServicePlanBuilder()
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
    assertThat(appServicePlan.getRegion()).isEqualTo(SELECTED_REGION);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionAndOperatingSystem() {
    var appServicePlan = getAppServicePlanBuilder()
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
    assertThat(appServicePlan.getRegion()).isEqualTo(SELECTED_REGION);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(SELECTED_OPERATING_SYSTEM);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemAndPricingPlan() {
    var appServicePlan = getAppServicePlanBuilder()
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
    assertThat(appServicePlan.getRegion()).isEqualTo(SELECTED_REGION);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(SELECTED_OPERATING_SYSTEM);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(SELECTED_PRICING_PLAN);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemPricingPlanAndEnabledZoneRedundancy() {
    var appServicePlan = getAppServicePlanBuilder()
        .withZoneRedundancyEnabled()
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
    assertThat(appServicePlan.getRegion()).isEqualTo(SELECTED_REGION);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(SELECTED_OPERATING_SYSTEM);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(SELECTED_PRICING_PLAN);
    assertThat(appServicePlan.getZoneRedundancyEnabled()).isEqualTo(true);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemPricingPlanEnabledZoneRedundancyAndTags() {
    var tags = Map.of("tags_Key1", "tags_Value1", "tags_Key2", "tags_Value2");
    var appServicePlan = getAppServicePlanBuilder()
        .withZoneRedundancyEnabled()
        .withTags(tags)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
    assertThat(appServicePlan.getRegion()).isEqualTo(SELECTED_REGION);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(SELECTED_OPERATING_SYSTEM);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(SELECTED_PRICING_PLAN);
    assertThat(appServicePlan.getZoneRedundancyEnabled()).isEqualTo(true);
    assertThat(appServicePlan.getTags().size()).isEqualTo(2);
    assertThat(appServicePlan.getTags()).isEqualTo(tags);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemPricingPlanEnabledZoneRedundancyAndTag() {
    var tagKey = "tagKey";
    var tagValue = "tagValue";

    var appServicePlan = getAppServicePlanBuilder()
        .withZoneRedundancyEnabled()
        .withNumberOfWorkers(3)
        .withTag(tagKey, tagValue)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(APP_SERVICE_PLAN_NAME);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(AZURE_RESOURCE_GROUP);
    assertThat(appServicePlan.getRegion()).isEqualTo(SELECTED_REGION);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(SELECTED_OPERATING_SYSTEM);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(SELECTED_PRICING_PLAN);
    assertThat(appServicePlan.getZoneRedundancyEnabled()).isEqualTo(true);
    assertThat(appServicePlan.getNumberOfWorkers()).isEqualTo(3);
    assertThat(appServicePlan.getTags().size()).isEqualTo(1);
    assertThat(appServicePlan.getTags()).isEqualTo(Map.of(tagKey, tagValue));
  }

  private AzureAppServicePlan.AzureAppServicePlanBuilder getAppServicePlanBuilder() {
    return AzureAppServicePlan.builder()
        .withName(APP_SERVICE_PLAN_NAME)
        .withAzureResourceGroup(AZURE_RESOURCE_GROUP)
        .withRegion(SELECTED_REGION)
        .withOperatingSystem(SELECTED_OPERATING_SYSTEM)
        .withPricingPlan(SELECTED_PRICING_PLAN);
  }
}
