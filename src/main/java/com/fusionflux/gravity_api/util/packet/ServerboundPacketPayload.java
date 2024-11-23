package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

@Deprecated
public non-sealed interface ServerboundPacketPayload extends BasePacketPayload {
    void handle(ServerPlayerEntity player, GravityComponent gc);

    static <T extends ServerboundPacketPayload> void handle(T packet, ServerPlayNetworking.Context ctx) {
        ServerPlayerEntity player = ctx.player();
        ctx.server().execute(() ->
                NetworkUtil.getGravityComponent(player).ifPresent(gc -> {
                    packet.handle(player, gc);
                    GravityPacketSender.sendToAllExceptSelf(ctx.server(), player, packet);
                })
        );
    }
}
