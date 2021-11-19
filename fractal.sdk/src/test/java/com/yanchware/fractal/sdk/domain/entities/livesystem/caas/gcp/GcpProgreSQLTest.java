package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpProgreSQL;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpRegion.ASIA_SOUTH1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GcpProgreSQLTest {

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithNoIdNoRegionNoNetwork() {
        var postgres = GcpProgreSQL.builder();
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "Component id has not been defined and it is required",
                "[GcpProgreSQL Validation] Region has not been defined and it is required",
                "[GcpProgreSQL Validation] Network has not been defined and it is required]"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithNoRegionNoNetwork() {
        var postgres = GcpProgreSQL.builder().withId(ComponentId.from("postg"));
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Region has not been defined and it is required",
                "[GcpProgreSQL Validation] Network has not been defined and it is required]"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithNoNetwork() {
        var postgres = GcpProgreSQL.builder()
                .withId(ComponentId.from("postg"))
                .region(ASIA_SOUTH1);
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Network has not been defined and it is required]"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkAddress() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkAddress("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkAddress() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkAddress("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkAddressDescription() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkAddressDescription("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address description is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkAddressDescription() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkAddressDescription("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address description is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkName() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkName("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network name is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkName() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkName("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network name is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkPrefix() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkPrefix("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network prefix is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkPrefix() {
        var postgres = getGcpPostgresBuilder()
                .peeringNetworkPrefix("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network prefix is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreated() {
        assertThat(getGcpPostgresBuilder().build().validate()).isEmpty();
    }

    private GcpProgreSQL.GcpPostgreSQLBuilder getGcpPostgresBuilder() {
        return GcpProgreSQL.builder()
                .withId(ComponentId.from("postg"))
                .region(ASIA_SOUTH1)
                .network("network");
    }
}