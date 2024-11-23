package com.fusionflux.gravity_api.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.HolderLookup;
import net.minecraft.world.World;

public class GravityDimensionStrengthComponent implements GravityDimensionStrengthInterface {
    double gravityStrength = 1;

    private final World currentWorld;

    public GravityDimensionStrengthComponent(World world) {
        this.currentWorld = world;
    }

    @Override
    public double getDimensionGravityStrength() {
        return gravityStrength;
    }

    @Override
    public void setDimensionGravityStrength(double strength) {
        if(!currentWorld.isClient) {
            gravityStrength = strength;
            GravityDimensionStrengthWorldRegister.GRAVITY_DIMENSION_STRENGTH_MODIFIER.sync(currentWorld);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
        gravityStrength = nbtCompound.getDouble("DimensionGravityStrength");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
        nbtCompound.putDouble("DimensionGravityStrength" , gravityStrength);

    }
}
