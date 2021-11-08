package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQLDB;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PostgreSQLDB extends PaaSPostgreSQLDB implements LiveSystemComponent {
    private final static String NAME_IS_BLANK = "PostgreSQLDB name has not been defined and it is required";

    private String name;
    private PostgreSQLCharset charset;
    private String collation;

    protected PostgreSQLDB() {
    }

    public static abstract class Builder<T extends PostgreSQLDB, B extends Builder<T, B>> extends Component.Builder<T, B> {

        public B name(String name) {
            component.setName(name);
            return builder;
        }

        public B charset(PostgreSQLCharset charset) {
            component.setCharset(charset);
            return builder;
        }

        public B collation(String collation) {
            component.setCollation(collation);
            return builder;
        }

        @Override
        public T build() {
            component.setType(POSTGRESQLDB);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(name)) {
            errors.add(NAME_IS_BLANK);
        }

        return errors;
    }
}
