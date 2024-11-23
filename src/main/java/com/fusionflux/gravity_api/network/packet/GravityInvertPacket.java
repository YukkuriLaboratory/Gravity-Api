package com.fusionflux.gravity_api.network.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.network.Networking;
import com.fusionflux.gravity_api.component.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;

public class GravityInvertPacket extends BasePacket {
    public static final PacketCodec<PacketByteBuf, GravityInvertPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            PacketCodecs.BOOL, p -> p.inverted,
            RotationParameters.PACKET_CODEC, p -> p.rotationParameters,
            PacketCodecs.BOOL, p -> p.initialGravity,
            GravityInvertPacket::new
    );

    public final boolean inverted;
    public final RotationParameters rotationParameters;
    public final boolean initialGravity;

    public GravityInvertPacket(int entityId, boolean inverted, RotationParameters rotationParameters, boolean initialGravity) {
        super(entityId);
        this.inverted = inverted;
        this.rotationParameters = rotationParameters;
        this.initialGravity = initialGravity;
    }

    @Override
    void onReceive(PlayerEntity player, GravityComponent gc) {
        gc.invertGravity(inverted, rotationParameters, initialGravity);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Networking.GRAVITY_INVERT.packetId();
    }
}
