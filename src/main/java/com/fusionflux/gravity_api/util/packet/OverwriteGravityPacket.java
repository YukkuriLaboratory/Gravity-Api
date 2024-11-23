package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Deprecated
public class OverwriteGravityPacket extends GravityPacket implements CilentboundAndServerboundPacketPayload {
    public static final PacketCodec<PacketByteBuf, OverwriteGravityPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            Gravity.PACKET_CODEC.apply(PacketCodecs.toCollection()), p -> p.gravityList,
            PacketCodecs.BOOL, p -> p.initialGravity,
            OverwriteGravityPacket::new
    );

    public final List<Gravity> gravityList;
    public final boolean initialGravity;

    public OverwriteGravityPacket(int _entityId, List<Gravity> _gravityList, boolean _initialGravity){
        entityId = _entityId;
        gravityList = _gravityList;
        initialGravity = _initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.setGravity(gravityList, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        Optional<Gravity> max = gravityList.stream().max(Comparator.comparingInt(Gravity::priority));
        if(max.isEmpty()) return new RotationParameters();
        return max.get().rotationParameters();
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return GravityPackets.OVERWRITE_GRAVITY_PACKET;
    }
}
