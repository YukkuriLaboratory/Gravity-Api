package com.fusionflux.gravity_api.entity;

import com.fusionflux.gravity_api.GravityChangerMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class EntityTags {
    public static final TagKey<EntityType<?>> FORBIDDEN_ENTITIES = TagKey.of(Registries.ENTITY_TYPE.getKey(), GravityChangerMod.id( "forbidden_entities"));
    public static final TagKey<EntityType<?>> FORBIDDEN_ENTITY_RENDERING = TagKey.of(Registries.ENTITY_TYPE.getKey(), GravityChangerMod.id( "forbidden_entity_rendering"));

    public static boolean canChangeGravity(Entity entity) {
            return !entity.getType().isIn(FORBIDDEN_ENTITIES);
    }
}
