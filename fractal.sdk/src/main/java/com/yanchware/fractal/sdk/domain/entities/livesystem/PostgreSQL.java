package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQL;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PostgreSQL extends PaaSPostgreSQL implements LiveSystemComponent {
    public static final String TYPE = POSTGRESQL.getId();

    private Collection<PostgreSQLDB> databases;

    protected PostgreSQL() {
        databases = new ArrayList<>();
    }

    public static abstract class Builder<T extends PostgreSQL, B extends Builder<T, B>> extends Component.Builder<T, B> {

        public B database(PostgreSQLDB db) {
            if (component.getDatabases() == null) {
                component.setDatabases(new ArrayList<>());
            }

            if (db != null) {
                component.getDatabases().add(db);
            }
            return builder;
        }

        public B databases(Collection<? extends PostgreSQLDB> dbs) {
            if (component.getDatabases() == null) {
                component.setDatabases(new ArrayList<>());
            }

            if (dbs == null) {
                dbs = new ArrayList<>();
            }

            component.getDatabases().addAll(dbs);
            return builder;
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        databases.stream()
                .map(PostgreSQLDB::validate)
                .forEach(errors::addAll);

        return errors;
    }
}
