package com.fusionflux.gravity_api.network.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.network.Networking;
import com.fusionflux.gravity_api.component.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.math.Direction;

public class GravitySyncPacket extends BasePacket {
    public static final PacketCodec<PacketByteBuf, GravitySyncPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            Direction.PACKET_CODEC, p -> p.direction,
            RotationParameters.PACKET_CODEC, p -> p.rotationParameters,
            PacketCodecs.BOOL, p -> p.initialGravity,
            GravitySyncPacket::new
    );

    public final Direction direction;
    public final RotationParameters rotationParameters;
    public final boolean initialGravity;

    public GravitySyncPacket(int entityId, Direction direction, RotationParameters rotationParameters, boolean initialGravity) {
        super(entityId);
        this.direction = direction;
        this.rotationParameters = rotationParameters;
        this.initialGravity = initialGravity;
    }

    @Override
    void onReceive(PlayerEntity player, GravityComponent gc) {
        gc.setDefaultGravityDirection(direction, rotationParameters, initialGravity);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Networking.GRAVITY_SYNC.packetId();
    }
}
