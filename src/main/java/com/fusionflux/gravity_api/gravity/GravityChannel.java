package com.fusionflux.gravity_api.gravity;

import com.fusionflux.gravity_api.GravityChangerMod;
import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.NetworkUtil.PacketMode;
import com.fusionflux.gravity_api.util.packet.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

import static com.fusionflux.gravity_api.util.NetworkUtil.*;

public class GravityChannel<P extends BasePacketPayload> {
    // TODO rewrite all
    public static GravityChannel<OverwriteGravityPacket> OVERWRITE_GRAVITY = new GravityChannel<>(OverwriteGravityPacket.PACKET_CODEC, GravityChangerMod.id("overwrite_gravity_list"));
    public static GravityChannel<UpdateGravityPacket> UPDATE_GRAVITY = new GravityChannel<>(UpdateGravityPacket::new, GravityChangerMod.id("update_gravity_list"));
    public static GravityChannel<DefaultGravityPacket> DEFAULT_GRAVITY = new GravityChannel<>(DefaultGravityPacket::new, GravityChangerMod.id("default_gravity"));
    public static GravityChannel<DefaultGravityStrengthPacket> DEFAULT_GRAVITY_STRENGTH = new GravityChannel<>(DefaultGravityStrengthPacket::new, GravityChangerMod.id("default_gravity_strength"));
    public static GravityChannel<InvertGravityPacket> INVERT_GRAVITY = new GravityChannel<>(InvertGravityPacket::new, GravityChangerMod.id("inverted"));

    private final PacketCodec<PacketByteBuf, P> codec;
    private final Identifier channel;
    private final GravityVerifierRegistry<P> gravityVerifierRegistry;

    GravityChannel(PacketCodec<PacketByteBuf, P> _codec, Identifier _channel){
        codec = _codec;
        channel = _channel;
        gravityVerifierRegistry = new GravityVerifierRegistry<>();
    }

    public void sendToClient(Entity entity, P packet, PacketMode mode){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entity.getId());
//        packet.write(buf);
        // TODO pass the value of entityId
        sendToTracking(entity, channel, buf, mode);
    }

//    public void receiveFromServer(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
//        int entityId = buf.readInt();
//        P packet = packetFactory.read(buf);
//        client.execute(() -> {
//            getGravityComponent(client, entityId).ifPresent(packet::run);
//        });
//    }

    public void sendToServer(P packet, Identifier verifier, PacketByteBuf verifierInfoBuf){
        PacketByteBuf buf = PacketByteBufs.create();
//        packet.write(buf);
        // TODO pass the value of entityId
//        buf.writeIdentifier(verifier);
//        buf.writeByteArray(verifierInfoBuf.array());
        codec.encode(buf, packet);
        ClientPlayNetworking.send(packet);
    }

//    public void receiveFromClient(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
//        P packet = packetFactory.read(buf);
//        Identifier verifier = buf.readIdentifier();
//        PacketByteBuf verifierInfoBuf = PacketByteBufs.create();
//        verifierInfoBuf.writeBytes(buf.readByteArray());
//        server.execute(() -> {
//            getGravityComponent(player).ifPresent(gc -> {
//                GravityVerifierRegistry.VerifierFunction<P> v = gravityVerifierRegistry.get(verifier);
//                if (v != null && v.check(player, verifierInfoBuf, packet)) {
//                    packet.run(gc);
//                    sendToClient(player, packet, PacketMode.EVERYONE_BUT_SELF);
//                }else {
//                    GravityChangerMod.LOGGER.info("VerifierFunction returned FALSE");
//                    sendFullStatePacket(player, PacketMode.ONLY_SELF, packet.getRotationParameters(), false);
//                }
//            });
//        });
//    }

    public static void sendFullStatePacket(Entity entity, PacketMode mode, RotationParameters rp, boolean initialGravity){
        getGravityComponent(entity).ifPresent(gc -> {
            OVERWRITE_GRAVITY.sendToClient(entity, new OverwriteGravityPacket(gc.getGravity(), initialGravity), mode);
            DEFAULT_GRAVITY.sendToClient(entity, new DefaultGravityPacket(gc.getDefaultGravityDirection(), rp, initialGravity), mode);
            INVERT_GRAVITY.sendToClient(entity, new InvertGravityPacket(gc.getInvertGravity(), rp, initialGravity), mode);
            DEFAULT_GRAVITY_STRENGTH.sendToClient(entity, new DefaultGravityStrengthPacket(gc.getGravityStrength()), mode);
        });
    }

    public GravityVerifierRegistry<P> getVerifierRegistry(){
        return gravityVerifierRegistry;
    }

//    public void registerClientReceiver(){
//        ClientPlayNetworking.registerGlobalReceiver(channel, this::receiveFromServer);
//    }
//
//    public void registerServerReceiver(){
//        ServerPlayNetworking.registerGlobalReceiver(channel, this::receiveFromClient);
//    }

    @FunctionalInterface
    interface Factory<T extends GravityPacket> {
        T read(PacketByteBuf buf);
    }
}