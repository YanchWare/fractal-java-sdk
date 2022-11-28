package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
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
public class PostgreSQLDB extends PaaSPostgreSQLDB implements LiveSystemComponent {
    private final static String NAME_IS_BLANK = "PostgreSQLDB name has not been defined and it is required";
    private final static String COLLATION_IS_BLANK = "PostgreSQLDB collation defined was either empty or blank and it is required";
    private final static String SCHEMA_IS_BLANK = "PostgreSQLDB schema defined was either empty or blank and it is required";

    private String name;
    private PostgreSQLCharset charset;
    private String collation;
    private String schema;
    private ProviderType provider;

    protected PostgreSQLDB() {
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static PostgreSQLDBBuilder builder() {
        return new PostgreSQLDBBuilder();
    }

    public static class PostgreSQLDBBuilder extends Builder<PostgreSQLDB, PostgreSQLDBBuilder> {

        @Override
        protected PostgreSQLDB createComponent() {
            return new PostgreSQLDB();
        }

        @Override
        protected PostgreSQLDBBuilder getBuilder() {
            return this;
        }

        public PostgreSQLDBBuilder withName(String name) {
            component.setName(name);
            return builder;
        }

        public PostgreSQLDBBuilder withCharset(PostgreSQLCharset charset) {
            component.setCharset(charset);
            return builder;
        }

        public PostgreSQLDBBuilder withCollation(String collation) {
            component.setCollation(collation);
            return builder;
        }

        public PostgreSQLDBBuilder withSchema(String schema) {
            component.setSchema(schema);
            return builder;
        }

        @Override
        public PostgreSQLDB build() {
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

        if(collation != null && isBlank(collation)) {
            errors.add(COLLATION_IS_BLANK);
        }

        if(schema != null && isBlank(schema)) {
            errors.add(SCHEMA_IS_BLANK);
        }

        return errors;
    }
}
