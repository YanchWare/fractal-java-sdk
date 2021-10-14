package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQLDB;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PostgreSQLDB extends PaaSPostgreSQLDB implements LiveSystemComponent {
    public static final String TYPE = POSTGRESQLDB.getId();
    private final static String NAME_IS_NULL_OR_EMPTY = "PostgreSQLDB name has not been defined and it is required";

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
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (StringUtils.isEmpty(name) || StringUtils.isBlank(name)) {
            errors.add(NAME_IS_NULL_OR_EMPTY);
        }

        return errors;
    }
}
