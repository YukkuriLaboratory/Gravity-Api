package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.Direction;

public class DefaultGravityPacket extends GravityPacket implements CilentboundAndServerboundPacketPayload {
    public static final PacketCodec<PacketByteBuf, DefaultGravityPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            Direction.PACKET_CODEC, p -> p.direction,
            RotationParameters.PACKET_CODEC, p -> p.rotationParameters,
            PacketCodecs.BOOL, p -> p.initialGravity,
            DefaultGravityPacket::new
    );
    public final Direction direction;
    public final RotationParameters rotationParameters;
    public final boolean initialGravity;

    public DefaultGravityPacket(int _entityId, Direction _direction, RotationParameters _rotationParameters, boolean _initialGravity){
        entityId = _entityId;
        direction = _direction;
        rotationParameters = _rotationParameters;
        initialGravity = _initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.setDefaultGravityDirection(direction, rotationParameters, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return rotationParameters;
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return GravityPackets.DEFAULT_GRAVITY_PACKET;
    }
}