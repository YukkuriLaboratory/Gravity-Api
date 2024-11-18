package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class UpdateGravityPacket extends GravityPacket implements CilentboundAndServerboundPacketPayload{
    public static final PacketCodec<PacketByteBuf, UpdateGravityPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            Gravity.PACKET_CODEC, p -> p.gravity,
            PacketCodecs.BOOL, p -> p.initialGravity,
            UpdateGravityPacket::new
    );
    public final Gravity gravity;
    public final boolean initialGravity;

    public UpdateGravityPacket(int _entityId, Gravity _gravity, boolean _initialGravity) {
        entityId = _entityId;
        gravity =  _gravity;
        initialGravity = _initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.addGravity(gravity, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return gravity.rotationParameters();
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return GravityPackets.UPDATE_GRAVITY_PACKET;
    }
}
