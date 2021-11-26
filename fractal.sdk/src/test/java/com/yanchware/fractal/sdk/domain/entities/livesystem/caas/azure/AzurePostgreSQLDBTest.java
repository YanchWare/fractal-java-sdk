package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PostgreSQLDB;
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
        var db = PostgreSQLDB.builder().name("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankName() {
        var db = PostgreSQLDB.builder().name("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void noValidationError_when_dbWithValidName() {
        var pgdb = PostgreSQLDB.builder().withId(ComponentId.from("dbid")).name("db").build();
        assertThat(pgdb.validate()).isEmpty();
        assertThat(pgdb.getCharset()).isNull();
        assertThat(pgdb.getCollation()).isNull();
    }

}