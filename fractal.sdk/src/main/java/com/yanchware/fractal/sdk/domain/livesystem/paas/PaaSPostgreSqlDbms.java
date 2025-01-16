package com.yanchware.fractal.sdk.domain.livesystem.paas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSRelationalDbms;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_POSTGRESQL_DBMS;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PaaSPostgreSqlDbms extends PaaSRelationalDbms implements LiveSystemComponent {
  private Collection<PaaSPostgreSqlDatabase> databases;

  protected PaaSPostgreSqlDbms() {
    databases = new ArrayList<>();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    databases.stream()
      .map(PaaSPostgreSqlDatabase::validate)
      .forEach(errors::addAll);

    return errors;
  }

  public static abstract class Builder<T extends PaaSPostgreSqlDbms, B extends Builder<T, B>> extends Component.Builder<T, B> {

    @Override
    public T build() {
      component.setType(PAAS_POSTGRESQL_DBMS);
      return super.build();
    }
  }
}
