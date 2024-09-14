package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp.GcpRegion.ASIA_SOUTH1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GcpProgreSqlTest {

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithNoIdNoRegionNoNetwork() {
        var postgres = GcpPostgreSqlDbms.builder();
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "Component id has not been defined and it is required",
                "[GcpProgreSQL Validation] Region has not been defined and it is required",
                "[GcpProgreSQL Validation] Network has not been defined and it is required]"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithNoRegionNoNetwork() {
        var postgres = GcpPostgreSqlDbms.builder().withId(ComponentId.from("postg"));
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Region has not been defined and it is required",
                "[GcpProgreSQL Validation] Network has not been defined and it is required]"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithNoNetwork() {
        var postgres = GcpPostgreSqlDbms.builder()
                .withId(ComponentId.from("postg"))
                .withRegion(ASIA_SOUTH1);
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Network has not been defined and it is required]"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkAddress() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkAddress("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkAddress() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkAddress("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkAddressDescription() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkAddressDescription("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address description is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkAddressDescription() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkAddressDescription("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network address description is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkName() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkName("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network name is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkName() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkName("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network name is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithEmptyPeeringNetworkPrefix() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkPrefix("");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network prefix is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreatedWithBlankPeeringNetworkPrefix() {
        var postgres = getGcpPostgresBuilder()
                .withPeeringNetworkPrefix("   ");
        assertThatThrownBy(postgres::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[GcpProgreSQL Validation] Peering network prefix is empty"
        );
    }

    @Test
    public void exceptionThrown_when_gcpPgCreated() {
        assertThat(getGcpPostgresBuilder().build().validate()).isEmpty();
    }

    private GcpPostgreSqlDbms.GcpPostgreSqlBuilder getGcpPostgresBuilder() {
        return GcpPostgreSqlDbms.builder()
                .withId(ComponentId.from("postg"))
                .withRegion(ASIA_SOUTH1)
                .withNetwork("network");
    }
}