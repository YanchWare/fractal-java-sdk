package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureFtpsState extends ExtendableEnum<AzureFtpsState> {
    public static final AzureFtpsState ALL_ALLOWED = fromString("AllAllowed");
    public static final AzureFtpsState DISABLED = fromString("Disabled");
    public static final AzureFtpsState FTPS_ONLY = fromString("FtpsOnly");
    
    public AzureFtpsState() {
    }

    @JsonCreator
    public static AzureFtpsState fromString(String name) {
        return fromString(name, AzureFtpsState.class);
    }

    public static Collection<AzureFtpsState> values() {

        return values(AzureFtpsState.class);
    }
}
