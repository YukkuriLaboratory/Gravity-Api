package com.fusionflux.gravity_api.item;

import com.fusionflux.gravity_api.GravityChangerMod;
import com.fusionflux.gravity_api.util.packet.DefaultGravityPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Verifier {
    public static Identifier FIELD_GRAVITY_SOURCE = GravityChangerMod.id( "changer_item");

    public static boolean check(ServerPlayerEntity player, PacketByteBuf info, DefaultGravityPacket packet) {

        if(packet.direction == null) return false;
        BlockPos blockPos = info.readPos();
        World world = player.getWorld();
        if(world == null) return false;
        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        BlockState blockState = world.getBlockState(blockPos);
        /*Return true if the block is a field generator or plating and could have triggered the gravity change.*/
        return true;
    }

    public static PacketByteBuf packInfo(BlockPos block){
        var buf = PacketByteBufs.create();
        buf.writePos(block);
        return buf;
    }

}
