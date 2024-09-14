package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSServiceMeshSecurity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class CaaSServiceMeshSecurityImpl extends CaaSServiceMeshSecurity implements LiveSystemComponent {
    private final static String HOST_IS_NULL_OR_EMPTY = "[CaaSServiceMeshSecurity Validation] Host has not been defined and it is required";
    private final static String HOST_OWNER_EMAIL_IS_NULL_OR_EMPTY = "[CaaSServiceMeshSecurity Validation] Host Owner Email has not been defined and it is required";
    private final static String COOKIE_MAX_AGE_IS_NULL_OR_EMPTY = "[CaaSServiceMeshSecurity Validation] Cookie max age has not been defined or is less than or equal to 0 and must be a positive number (in seconds)";

    private String host;
    private String hostOwnerEmail;
    private List<String> corsOrigins;
    private int cookieMaxAgeSec;
    private String pathPrefix;

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(host)) {
            errors.add(HOST_IS_NULL_OR_EMPTY);
        }

        if (isBlank(hostOwnerEmail)) {
            errors.add(HOST_OWNER_EMAIL_IS_NULL_OR_EMPTY);
        }

        if (cookieMaxAgeSec <= 0) {
            errors.add(COOKIE_MAX_AGE_IS_NULL_OR_EMPTY);
        }
        return errors;
    }
}
