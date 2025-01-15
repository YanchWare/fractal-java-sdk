package com.yanchware.fractal.sdk.domain.livesystem.iaas;

public record Route(String name, String destinationAddressSpaceCidr, NetworkHop nextNetworkHop) {
}
