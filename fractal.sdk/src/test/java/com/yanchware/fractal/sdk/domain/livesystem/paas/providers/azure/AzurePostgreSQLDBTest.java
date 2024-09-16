package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AzurePostgreSQLDBTest {

    @Test
    public void validationError_when_dbWithNullName() {
        var db = AzurePostgreSqlDatabase.builder();
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("[AzurePostgreSqlDatabase Validation] name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptyName() {
        var db = AzurePostgreSqlDatabase.builder().withName("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("[AzurePostgreSqlDatabase Validation] name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankName() {
        var db = AzurePostgreSqlDatabase.builder().withName("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("[AzurePostgreSqlDatabase Validation] name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankCollation() {
        var db = AzurePostgreSqlDatabase.builder().withCollation("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB collation defined was either empty or blank and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptyCollation() {
        var db = AzurePostgreSqlDatabase.builder().withCollation("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB collation defined was either empty or blank and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankSchema() {
        var db = AzurePostgreSqlDatabase.builder().withSchema("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB schema defined was either empty or blank and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptySchema() {
        var db = AzurePostgreSqlDatabase.builder().withSchema("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB schema defined was either empty or blank and it is required");
    }

    @Test
    public void noValidationError_when_dbWithValidName() {
        var pgdb = AzurePostgreSqlDatabase.builder().withId(ComponentId.from("dbid")).withName("db").build();
        assertThat(pgdb.validate()).isEmpty();
        assertThat(pgdb.getCharset()).isNull();
        assertThat(pgdb.getCollation()).isNull();
        assertThat(pgdb.getSchema()).isNull();
    }

}