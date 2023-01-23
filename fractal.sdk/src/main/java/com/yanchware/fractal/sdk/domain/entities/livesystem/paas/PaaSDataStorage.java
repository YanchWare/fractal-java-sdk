package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PaaSDataStorage extends com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDataStorage
    implements LiveSystemComponent {

    protected PaaSDataStorage() {
    }

    public static abstract class Builder<T extends PaaSDataStorage, B extends Builder<T, B>> extends Component.Builder<T, B> {

        @Override
        public T build() {
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        return errors;
    }
}
