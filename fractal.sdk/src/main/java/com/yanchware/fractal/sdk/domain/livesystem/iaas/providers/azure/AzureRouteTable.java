package com.yanchware.fractal.sdk.domain.livesystem.iaas.providers.azure;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.IaaSRouteTable;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.iaas.Route;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureRouteTable extends IaaSRouteTable implements AzureResourceEntity, LiveSystemComponent {
    private AzureRegion azureRegion;
    private AzureResourceGroup azureResourceGroup;
    private Map<String, String> tags;

    private List<Route> routes;

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

}
