package com.fusionflux.gravity_api.util.packet;


import com.fusionflux.gravity_api.GravityChangerMod;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.payload.CustomPayload;

import java.util.Locale;

@Deprecated
public enum GravityPackets implements BasePacketPayload.PacketTypeProvider {
    // Client to Server & Server to Client
    DEFAULT_GRAVITY_PACKET(DefaultGravityPacket.class, DefaultGravityPacket.PACKET_CODEC),
    DEFAULT_GRAVITY_STRENGTH_PACKET(DefaultGravityStrengthPacket.class, DefaultGravityStrengthPacket.PACKET_CODEC),
    INVERT_GRAVITY_PACKET(InvertGravityPacket.class, InvertGravityPacket.PACKET_CODEC),
    OVERWRITE_GRAVITY_PACKET(OverwriteGravityPacket.class, OverwriteGravityPacket.PACKET_CODEC),
    UPDATE_GRAVITY_PACKET(UpdateGravityPacket.class, UpdateGravityPacket.PACKET_CODEC)
    ;

    private final PacketType<?> type;

    <T extends BasePacketPayload> GravityPackets(Class<T> clazz, PacketCodec<? super RegistryByteBuf, T> codec) {
        String name = this.name().toLowerCase(Locale.ROOT);
        this.type = new PacketType<>(
                new CustomPayload.Id<>(GravityChangerMod.id(name)),
                clazz, codec
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CustomPayload> CustomPayload.Id<T> getId() {
        return (CustomPayload.Id<T>) this.type.type();
    }

    public static void registerPackets() {
        for (GravityPackets packet : values()) {
            packet.type.register();
        }
    }

    private record PacketType<T extends BasePacketPayload>(CustomPayload.Id<T> type, Class<T> clazz, PacketCodec<? super RegistryByteBuf, T> codec) {
        @SuppressWarnings("unchecked")
        public void register() {
            boolean clientbound = ClientboundPacketPayload.class.isAssignableFrom(this.clazz);
            boolean serverbound = ServerboundPacketPayload.class.isAssignableFrom(this.clazz);
            if (clientbound) {
                PacketType<ClientboundPacketPayload> casted = (PacketType<ClientboundPacketPayload>) this;
                PayloadTypeRegistry.playS2C().register(casted.type, casted.codec);
                ClientPlayNetworking.registerGlobalReceiver(casted.type, ClientboundPacketPayload::handle);
            }
            if (serverbound) {
                PacketType<ServerboundPacketPayload> casted = (PacketType<ServerboundPacketPayload>) this;
                PayloadTypeRegistry.playC2S().register(casted.type, casted.codec);
                ServerPlayNetworking.registerGlobalReceiver(casted.type, ServerboundPacketPayload::handle);
            }
        }
    }
}
