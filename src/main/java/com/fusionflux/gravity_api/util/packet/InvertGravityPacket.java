package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class InvertGravityPacket extends GravityPacket implements CilentboundAndServerboundPacketPayload {
    public static final PacketCodec<PacketByteBuf, InvertGravityPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            PacketCodecs.BOOL, p -> p.inverted,
            RotationParameters.PACKET_CODEC, p -> p.rotationParameters,
            PacketCodecs.BOOL, p -> p.initialGravity,
            InvertGravityPacket::new
    );

    public final boolean inverted;
    public final RotationParameters rotationParameters;
    public final boolean initialGravity;

    public InvertGravityPacket(int _entityId, boolean _inverted, RotationParameters _rotationParameters, boolean _initialGravity){
        entityId = _entityId;
        inverted = _inverted;
        rotationParameters = _rotationParameters;
        initialGravity = _initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.invertGravity(inverted, rotationParameters, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return rotationParameters;
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return GravityPackets.INVERT_GRAVITY_PACKET;
    }
}
