package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureProxyResource;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/*
  Private key certificates (.pfx) can be used for TLS/SSL bindings and can be loaded to the certificate store for your app to consume. 
 */
@Getter
public class AzureKeyVaultCertificate extends AzureProxyResource implements Validatable {
  private final static String KEY_VAULT_ID_IS_BLANK = "[AzureKeyVaultCertificate Validation] KeyVaultId has not been defined and it is required";
  private final static String NAME_IS_BLANK = "[AzureKeyVaultCertificate Validation] Name has not been defined and it is required";
  private final String keyVaultId;
  private final String password;

  public AzureKeyVaultCertificate(String name,
                                  AzureRegion region,
                                  Map<String, String> tags,
                                  String keyVaultId,
                                  String password) {
    super(name, region, tags);
    
    this.keyVaultId = keyVaultId;
    this.password = password;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder extends AzureProxyResource.Builder<Builder> {
    protected String keyVaultId;
    protected String password;

    public Builder withKeyVaultId(String keyVaultId) {
      this.keyVaultId = keyVaultId;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    @Override
    public AzureKeyVaultCertificate build(){
      var certificate = new AzureKeyVaultCertificate(name, region, tags, keyVaultId, password);
      Collection<String> errors = certificate.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureKeyVaultCertificate validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return certificate;
    }

  }


  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (isBlank(keyVaultId)) {
      errors.add(KEY_VAULT_ID_IS_BLANK);
    }

    if (isBlank(getName())) {
      errors.add(NAME_IS_BLANK);
    }

    return errors;
  }
}
