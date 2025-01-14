package com.fusionflux.gravity_api.item;

import com.fusionflux.gravity_api.GravityChangerMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;


public abstract class ModItems {
    public static final Item GRAVITY_CHANGER_DOWN = new GravityChangerItem(new Item.Settings().maxCount(1), Direction.DOWN);
    public static final Item GRAVITY_CHANGER_UP = new GravityChangerItem(new Item.Settings().maxCount(1), Direction.UP);
    public static final Item GRAVITY_CHANGER_NORTH = new GravityChangerItem(new Item.Settings().maxCount(1), Direction.NORTH);
    public static final Item GRAVITY_CHANGER_SOUTH = new GravityChangerItem(new Item.Settings().maxCount(1), Direction.SOUTH);
    public static final Item GRAVITY_CHANGER_WEST = new GravityChangerItem(new Item.Settings().maxCount(1), Direction.WEST);
    public static final Item GRAVITY_CHANGER_EAST = new GravityChangerItem(new Item.Settings().maxCount(1), Direction.EAST);

    public static final Item GRAVITY_CHANGER_DOWN_AOE = new GravityChangerItemAOE(new Item.Settings().maxCount(1), Direction.DOWN);
    public static final Item GRAVITY_CHANGER_UP_AOE = new GravityChangerItemAOE(new Item.Settings().maxCount(1), Direction.UP);
    public static final Item GRAVITY_CHANGER_NORTH_AOE = new GravityChangerItemAOE(new Item.Settings().maxCount(1), Direction.NORTH);
    public static final Item GRAVITY_CHANGER_SOUTH_AOE = new GravityChangerItemAOE(new Item.Settings().maxCount(1), Direction.SOUTH);
    public static final Item GRAVITY_CHANGER_WEST_AOE = new GravityChangerItemAOE(new Item.Settings().maxCount(1), Direction.WEST);
    public static final Item GRAVITY_CHANGER_EAST_AOE = new GravityChangerItemAOE(new Item.Settings().maxCount(1), Direction.EAST);

    public static void init() {
        register( "gravity_changer_down", GRAVITY_CHANGER_DOWN);
        register( "gravity_changer_up", GRAVITY_CHANGER_UP);
        register( "gravity_changer_north", GRAVITY_CHANGER_NORTH);
        register( "gravity_changer_south", GRAVITY_CHANGER_SOUTH);
        register( "gravity_changer_west", GRAVITY_CHANGER_WEST);
        register( "gravity_changer_east", GRAVITY_CHANGER_EAST);


        register( "gravity_changer_down_aoe", GRAVITY_CHANGER_DOWN_AOE);
        register( "gravity_changer_up_aoe", GRAVITY_CHANGER_UP_AOE);
        register( "gravity_changer_north_aoe", GRAVITY_CHANGER_NORTH_AOE);
        register( "gravity_changer_south_aoe", GRAVITY_CHANGER_SOUTH_AOE);
        register( "gravity_changer_west_aoe", GRAVITY_CHANGER_WEST_AOE);
        register( "gravity_changer_east_aoe", GRAVITY_CHANGER_EAST_AOE);
    }

    private static void register(String name, Item item) {
        Registry.register(Registries.ITEM, GravityChangerMod.id(name), item);
    }
}
