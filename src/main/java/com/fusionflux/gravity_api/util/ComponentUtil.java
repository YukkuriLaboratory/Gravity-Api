package com.fusionflux.gravity_api.util;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.component.GravityComponent;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Util class for {@link GravityComponent}
 */
public class ComponentUtil {
    /**
     * Get GravityComponent by entityId
     * @param world world of Client
     * @param entityId id of target entity
     * @return returns GravityComponent. if failed, return null.
     */
    public static Optional<GravityComponent> getGravityComponentByEntityId(@NotNull ClientWorld world, int entityId) {
        Entity entity = world.getEntityById(entityId);
        if(entity != null) {
            return getGravityComponentByEntity(entity);
        }
        return Optional.empty(); // default return value
    }

    public static Optional<GravityComponent> getGravityComponentByEntity(Entity entity) {
        return Optional.ofNullable(GravityChangerAPI.getGravityComponent(entity));
    }
}
