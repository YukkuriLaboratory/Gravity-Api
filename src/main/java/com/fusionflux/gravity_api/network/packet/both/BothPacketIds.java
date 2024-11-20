package com.fusionflux.gravity_api.network.packet.both;

import com.fusionflux.gravity_api.GravityChangerMod;
import net.minecraft.util.Identifier;

/**
 * A class to hold both side packet ids
 */
public class BothPacketIds {
    // Gravity packet ids
    public static final Identifier GRAVITY_SYNC = id("gravity_sync");
    public static final Identifier GRAVITY_UPDATE = id("gravity_update");
    public static final Identifier GRAVITY_INVERT = id("gravity_invert");
    public static final Identifier GRAVITY_OVERWRITE = id("gravity_overwrite");

    // Gravity strength packet ids
    public static final Identifier GRAVITY_STRENGTH_SYNC = id("gravity_strength_sync");

    /**
     * Wrapper for writing readable code.
     * @param path non-prefixed packet's path
     * @return Identifier for both side packet
     */
    private static Identifier id(String path) {
        return GravityChangerMod.id("both/" + path);
    }
}
