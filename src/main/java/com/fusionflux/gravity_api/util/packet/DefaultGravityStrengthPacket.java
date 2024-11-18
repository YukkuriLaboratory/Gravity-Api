package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class DefaultGravityStrengthPacket extends GravityPacket implements CilentboundAndServerboundPacketPayload {
    public static final PacketCodec<PacketByteBuf, DefaultGravityStrengthPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            PacketCodecs.DOUBLE, p -> p.strength,
            DefaultGravityStrengthPacket::new
    );

    public final double strength;

    public DefaultGravityStrengthPacket(int _entityId, double _strength){
        entityId = _entityId;
        strength = _strength;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.setDefaultGravityStrength(strength);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return null;
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return GravityPackets.DEFAULT_GRAVITY_STRENGTH_PACKET;
    }
}