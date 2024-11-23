package com.fusionflux.gravity_api.network.packet;

import com.fusionflux.gravity_api.network.Networking;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;

public class GravityUpdatePacket extends BasePacket {
    public static final PacketCodec<PacketByteBuf, GravityUpdatePacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            Gravity.PACKET_CODEC, p -> p.gravity,
            PacketCodecs.BOOL, p -> p.initialGravity,
            GravityUpdatePacket::new
    );
    public final Gravity gravity;
    public final boolean initialGravity;

    public GravityUpdatePacket(int entityId, Gravity gravity, boolean initialGravity) {
        super(entityId);
        this.gravity = gravity;
        this.initialGravity = initialGravity;
    }

    @Override
    void onReceive(PlayerEntity player, GravityComponent gc) {
        gc.addGravity(gravity, initialGravity);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Networking.GRAVITY_UPDATE.packetId();
    }
}
