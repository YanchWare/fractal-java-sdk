package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PostgreSQLDB;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AzurePostgreSQLDBTest {

    @Test
    public void validationError_when_dbWithNullName() {
        var db = PostgreSQLDB.builder();
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptyName() {
        var db = PostgreSQLDB.builder().withName("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankName() {
        var db = PostgreSQLDB.builder().withName("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankCollation() {
        var db = PostgreSQLDB.builder().withCollation("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB collation defined was either empty or blank and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptyCollation() {
        var db = PostgreSQLDB.builder().withCollation("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB collation defined was either empty or blank and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankSchema() {
        var db = PostgreSQLDB.builder().withSchema("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB schema defined was either empty or blank and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptySchema() {
        var db = PostgreSQLDB.builder().withSchema("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB schema defined was either empty or blank and it is required");
    }

    @Test
    public void noValidationError_when_dbWithValidName() {
        var pgdb = PostgreSQLDB.builder().withId(ComponentId.from("dbid")).withName("db").build();
        assertThat(pgdb.validate()).isEmpty();
        assertThat(pgdb.getCharset()).isNull();
        assertThat(pgdb.getCollation()).isNull();
        assertThat(pgdb.getSchema()).isNull();
    }

}