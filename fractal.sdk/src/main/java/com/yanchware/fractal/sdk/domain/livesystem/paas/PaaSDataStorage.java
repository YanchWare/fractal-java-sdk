package com.yanchware.fractal.sdk.domain.livesystem.paas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PaaSDataStorage extends com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSDataStorage
  implements LiveSystemComponent
{

  protected PaaSDataStorage() {
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    return errors;
  }

  public static abstract class Builder<T extends PaaSDataStorage, B extends Builder<T, B>> extends Component.Builder<T, B> {

    @Override
    public T build() {
      return super.build();
    }
  }
}
