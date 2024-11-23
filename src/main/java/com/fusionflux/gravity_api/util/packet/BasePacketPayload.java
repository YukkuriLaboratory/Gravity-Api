package com.fusionflux.gravity_api.util.packet;

import net.minecraft.network.packet.payload.CustomPayload;

@Deprecated
public sealed interface BasePacketPayload extends CustomPayload permits ClientboundPacketPayload, ServerboundPacketPayload {
    PacketTypeProvider getTypeProvider();

    @Override
    default CustomPayload.Id<? extends CustomPayload> getId() {
        return this.getTypeProvider().getId();
    }

    interface PacketTypeProvider {
        <T extends CustomPayload> CustomPayload.Id<T> getId();
    }
}
