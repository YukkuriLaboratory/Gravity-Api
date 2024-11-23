package com.fusionflux.gravity_api.network;

import com.fusionflux.gravity_api.GravityChangerMod;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Data holder of packet id
 * @param id Identifier of packet
 * @param packetId {@link CustomPayload.Id} of packet
 */
public record NetworkId(
        Identifier id,
        CustomPayload.Id<? extends CustomPayload> packetId
) {
    public static NetworkId of(@NotNull String path) {
        var id = createId(path);
        return new NetworkId(
                id,
                createPacketId(id)
        );
    }

    /**
     * @param path Path of packet
     * @return "packet/" prefixed identifier
     */
    private static Identifier createId(@NotNull String path) {
        return GravityChangerMod.id("packet/" + path);
    }

    private static <T extends CustomPayload> CustomPayload.Id<T> createPacketId(Identifier identifier) {
        return new CustomPayload.Id<>(identifier);
    }
}
