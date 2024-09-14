package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSRelationalDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_POSTGRESQL_DATABASE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class PaaSPostgreSqlDatabase extends PaaSRelationalDatabase implements LiveSystemComponent {
    private final static String COLLATION_IS_BLANK = "PostgreSQLDB collation defined was either empty or blank and it is required";
    private final static String SCHEMA_IS_BLANK = "PostgreSQLDB schema defined was either empty or blank and it is required";

    private String name;
    private PostgreSqlCharset charset;
    private String collation;
    private String schema;

    protected PaaSPostgreSqlDatabase() {
    }


    public static abstract class Builder<T extends PaaSPostgreSqlDatabase, B extends PaaSPostgreSqlDatabase.Builder<T, B>> extends Component.Builder<T, B> {

        /**
         * Name of the database
         * @param name
         */
        public B withName(String name) {
            component.setName(name);
            return builder;
        }

        /**
         * Charset of the database
         * @param charset
         */
        public B withCharset(PostgreSqlCharset charset) {
            component.setCharset(charset);
            return builder;
        }

        /**
         * Collation of the database
         * @param collation
         */
        public B withCollation(String collation) {
            component.setCollation(collation);
            return builder;
        }

        /**
         * Schema of the database
         * @param schema
         */
        public B withSchema(String schema) {
            component.setSchema(schema);
            return builder;
        }

        @Override
        public T build() {
            component.setType(PAAS_POSTGRESQL_DATABASE);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if(collation != null && isBlank(collation)) {
            errors.add(COLLATION_IS_BLANK);
        }

        if(schema != null && isBlank(schema)) {
            errors.add(SCHEMA_IS_BLANK);
        }

        return errors;
    }
}
