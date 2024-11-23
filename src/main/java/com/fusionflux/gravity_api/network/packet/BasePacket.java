package com.fusionflux.gravity_api.network.packet;

import com.fusionflux.gravity_api.component.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import com.fusionflux.gravity_api.util.packet.GravityPacketSender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.payload.CustomPayload;

/**
 * Packet base of Gravity Api
 */
public abstract class BasePacket implements CustomPayload {
    public final int entityId;

    public BasePacket(int entityId) {
        this.entityId = entityId;
    }
    /**
     * This method call on receive packet
     * @param player player
     * @param gc {@link GravityComponent} of player
     */
    abstract void onReceive(PlayerEntity player, GravityComponent gc);

    /**
     * CAUTION: This is Client side packet handler. Don't call from Server.
     * @param packet received packet
     * @param ctx context for handling
     * @param <T> extends BasePacket
     */
    @Environment(EnvType.CLIENT)
    public <T extends BasePacket> void handle(T packet, ClientPlayNetworking.Context ctx) {
        ctx.client().execute(() -> {
            NetworkUtil.getGravityComponent(ctx.client(), packet.entityId).ifPresent(gc -> {
                packet.onReceive(ctx.player(), gc);
            });
        });
    }

    /**
     * Server side packet handler.
     * @param packet packet
     * @param ctx context for handling
     * @param <T> extends BasePacket
     */
    public <T extends BasePacket> void handle(T packet, ServerPlayNetworking.Context ctx) {
        ctx.server().execute(() ->
                NetworkUtil.getGravityComponent(ctx.player()).ifPresent(gc -> {
                    packet.onReceive(ctx.player(), gc);
                    GravityPacketSender.sendToAllExceptSelf(ctx.server(), ctx.player(), packet);
                })
        );
    }
}
