package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_WEBAPP;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureWebApp extends PaaSWorkload implements AzureEntity, LiveSystemComponent, CustomWorkload {

  private String privateSSHKeyPassphraseSecretId;
  private String privateSSHKeySecretId;
  private String sshRepositoryURI;
  private String repoId;
  private String branchName;
  private List<CustomWorkloadRole> roles;
  private String workloadSecretIdKey;
  private String workloadSecretPasswordKey;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private AzureWebAppApplication application;
  private AzureWebAppHosting hosting;
  private AzureWebAppConnectivity connectivity;
  private AzureWebAppInfrastructure infrastructure;

  @Override
  public ProviderType getProvider(){
    return ProviderType.AZURE;
  }

  protected AzureWebApp() {
    roles = new ArrayList<>();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(CustomWorkload.validateCustomWorkload(this, "Azure Web App"));
    errors.addAll(hosting.validate());
    return errors;
  }

  public static AzureWebAppBuilder builder() {
    return new AzureWebAppBuilder();
  }

  public static class AzureWebAppBuilder extends CustomWorkloadBuilder<AzureWebApp, AzureWebAppBuilder> {

    @Override
    protected AzureWebApp createComponent() {
      return new AzureWebApp();
    }

    @Override
    protected AzureWebAppBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureWebApp build() {
      component.setType(PAAS_AZURE_WEBAPP);
      return super.build();
    }
    
    public AzureWebAppBuilder withApplication(AzureWebAppApplication application) {
      component.setApplication(application);
      return builder;
    }

    public AzureWebAppBuilder withConnectivity(AzureWebAppConnectivity connectivity) {
      component.setConnectivity(connectivity);
      return builder;
    }

    public AzureWebAppBuilder withHosting(AzureWebAppHosting hosting) {
      component.setHosting(hosting);
      return builder;
    }

    public AzureWebAppBuilder withInfrastructure(AzureWebAppInfrastructure infrastructure) {
      component.setInfrastructure(infrastructure);
      return builder;
    }
  }

}
