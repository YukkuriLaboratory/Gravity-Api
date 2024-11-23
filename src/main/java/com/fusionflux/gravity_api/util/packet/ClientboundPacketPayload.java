package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.component.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;

@Deprecated
public non-sealed interface ClientboundPacketPayload extends BasePacketPayload {
    @Environment(EnvType.CLIENT)
    void handle(ClientPlayerEntity player, GravityComponent gc);

    @Environment(EnvType.CLIENT)
    static <T extends ClientboundPacketPayload> void handle(T packet, ClientPlayNetworking.Context ctx) {
        ctx.client().execute(() ->
                NetworkUtil.getGravityComponent(ctx.client(), ((GravityPacket) packet).entityId).ifPresent(gc -> {
                    packet.handle(ctx.player(), gc);
                })
        );
    }
}
