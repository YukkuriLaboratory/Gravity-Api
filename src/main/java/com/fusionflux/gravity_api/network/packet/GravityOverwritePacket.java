package com.fusionflux.gravity_api.network.packet;

import com.fusionflux.gravity_api.network.Networking;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.GravityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;

import java.util.List;

public class GravityOverwritePacket extends BasePacket{
    public static final PacketCodec<PacketByteBuf, GravityOverwritePacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INT, p -> p.entityId,
            Gravity.PACKET_CODEC.apply(PacketCodecs.toCollection()), p -> p.gravityList,
            PacketCodecs.BOOL, p -> p.initialGravity,
            GravityOverwritePacket::new
    );

    public final List<Gravity> gravityList;
    public final boolean initialGravity;

    public GravityOverwritePacket(int entityId, List<Gravity> gravityList, boolean initialGravity) {
        super(entityId);
        this.gravityList = gravityList;
        this.initialGravity = initialGravity;
    }

    @Override
    void onReceive(PlayerEntity player, GravityComponent gc) {
        gc.setGravity(gravityList, initialGravity);
    }

    // TODO getRotationParametersの本家の実装がごにょごにょやってるから必要かも？

    @Override
    public Id<? extends CustomPayload> getId() {
        return Networking.GRAVITY_OVERWRITE.packetId();
    }
}
