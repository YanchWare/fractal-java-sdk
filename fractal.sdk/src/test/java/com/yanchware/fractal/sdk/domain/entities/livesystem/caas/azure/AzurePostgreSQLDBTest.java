package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzurePostgreSQLDB;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AzurePostgreSQLDBTest {

    @Test
    public void validationError_when_dbWithNullName() {
        var db = AzurePostgreSQLDB.builder();
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithEmptyName() {
        var db = AzurePostgreSQLDB.builder().name("");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void validationError_when_dbWithBlankName() {
        var db = AzurePostgreSQLDB.builder().name("   ");
        assertThatThrownBy(db::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PostgreSQLDB name has not been defined and it is required");
    }

    @Test
    public void noValidationError_when_dbWithValidName() {
        AzurePostgreSQLDB pgdb = AzurePostgreSQLDB.builder().withId(ComponentId.from("dbid")).name("db").build();
        assertThat(pgdb.validate()).isEmpty();
        assertThat(pgdb.getCharset()).isNull();
        assertThat(pgdb.getCollation()).isNull();
    }

}