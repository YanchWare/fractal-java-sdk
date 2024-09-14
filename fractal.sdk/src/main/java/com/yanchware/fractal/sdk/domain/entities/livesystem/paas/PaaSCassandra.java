package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSColumnOrientedDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_CASANDRA_DBMS;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PaaSCassandra extends PaaSColumnOrientedDbms implements LiveSystemComponent {
    public static final String TYPE = PAAS_CASANDRA_DBMS.getId();
    public static abstract class Builder<T extends PaaSCassandra, B extends Builder<T, B>> extends Component.Builder<T, B> {

        @Override
        public T build() {
            component.setType(PAAS_CASANDRA_DBMS);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        return super.validate();
    }
}
