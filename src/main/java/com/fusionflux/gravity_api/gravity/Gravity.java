package com.fusionflux.gravity_api.gravity;

import com.fusionflux.gravity_api.api.RotationParameters;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class Gravity {
    public static final PacketCodec<PacketByteBuf, Gravity> PACKET_CODEC = PacketCodec.tuple(
            Direction.PACKET_CODEC, Gravity::direction,
            PacketCodecs.INT, Gravity::priority,
            PacketCodecs.optional(PacketCodecs.DOUBLE), g -> Optional.of(g.strength()),
            PacketCodecs.INT, Gravity::duration,
            PacketCodecs.STRING, Gravity::source,
            PacketCodecs.optional(RotationParameters.PACKET_CODEC), g -> Optional.of(g.rotationParameters()),
            (direction, priority, strengthOptional, duration, source, rotationParametersOptional) ->
                    new Gravity(
                            direction,
                            priority,
                            strengthOptional.orElse(1D),
                            duration,
                            source,
                            rotationParametersOptional.orElse(new RotationParameters())
                    )
    );

    private final Direction direction;
    private final int priority;
    private int duration;
    private final double strength;
    private final String source;
    private final RotationParameters rotationParameters;
    public Gravity(Direction _direction, int _priority, double _strength, int _duration, String _source, RotationParameters _rotationParameters) {
        direction = _direction;
        priority = _priority;
        duration = _duration;
        source = _source;
        strength = _strength;
        rotationParameters = _rotationParameters;
    }

    public Gravity(Direction _direction, int _priority, int _duration, String _source, RotationParameters _rotationParameters) {
        this(_direction, _priority,1, _duration, _source, _rotationParameters);
    }
    public Gravity(Direction _direction, int _priority, int _duration, String _source) {
        this(_direction, _priority,1, _duration, _source, new RotationParameters());
    }

    public Gravity(Direction _direction, int _priority, double _strength, int _duration, String _source) {
        this(_direction, _priority, _strength, _duration, _source, new RotationParameters());
    }

    public Direction direction() {
        return direction;
    }
    public int duration() {
        return duration;
    }
    public double strength() {
        return strength;
    }
    public int priority() {
        return priority;
    }
    public String source() {
        return source;
    }
    public RotationParameters rotationParameters(){
        return rotationParameters;
    }

    public void decrementDuration() {
        duration--;
    }
}
