package com.fusionflux.gravity_api.network.packet;

import com.fusionflux.gravity_api.network.Networking;
import com.fusionflux.gravity_api.component.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;

public class GravityStrengthSyncPacket extends BasePacket {
    public static final PacketCodec<PacketByteBuf, GravityStrengthSyncPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            PacketCodecs.DOUBLE, p -> p.strength,
            GravityStrengthSyncPacket::new
    );

    public final double strength;

    public GravityStrengthSyncPacket(int entityId, double strength) {
        super(entityId);
        this.strength = strength;
    }

    @Override
    void onReceive(PlayerEntity player, GravityComponent gc) {

    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Networking.GRAVITY_STRENGTH_SYNC.packetId();
    }
}
