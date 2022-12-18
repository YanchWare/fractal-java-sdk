package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSql;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_POSTGRESQL;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PaaSPostgreSqlImpl extends PaaSPostgreSql implements LiveSystemComponent {
    private Collection<PaaSPostgreSqlDbImpl> databases;

    protected PaaSPostgreSqlImpl() {
        databases = new ArrayList<>();
    }

    public static abstract class Builder<T extends PaaSPostgreSqlImpl, B extends Builder<T, B>> extends Component.Builder<T, B> {

        @Override
        public T build() {
            component.setType(PAAS_POSTGRESQL);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        databases.stream()
                .map(PaaSPostgreSqlDbImpl::validate)
                .forEach(errors::addAll);

        return errors;
    }
}
