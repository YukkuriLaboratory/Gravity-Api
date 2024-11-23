package com.fusionflux.gravity_api.network;

public class Networking {
    // Gravity general packets
    public static final NetworkId GRAVITY_SYNC = NetworkId.of("gravity_sync");
    public static final NetworkId GRAVITY_UPDATE = NetworkId.of("gravity_update");
    public static final NetworkId GRAVITY_INVERT = NetworkId.of("gravity_invert");
    public static final NetworkId GRAVITY_OVERWRITE = NetworkId.of("gravity_overwrite");

    // Gravity strength packets
    public static final NetworkId GRAVITY_STRENGTH_SYNC = NetworkId.of("gravity_strength_sync");
}
