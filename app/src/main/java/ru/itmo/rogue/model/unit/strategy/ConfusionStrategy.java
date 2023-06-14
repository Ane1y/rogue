package ru.itmo.rogue.model.unit.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.PositionUpdate;

import java.util.Random;

/**
 * Strategy that randomly inverts or turns units decisions
 */
public class ConfusionStrategy implements Strategy {

    private final Strategy underlying;
    private int duration;
    private final Random random = new Random();


    public ConfusionStrategy(Strategy strategy, int duration) {
        this.underlying = strategy;
        this.duration = duration;
    }

    @Override
    public @NotNull StateUpdate getAction(UnitView unit, StateView state) {
        if (duration < 1) {
            return underlying.getAction(unit, state);
        }

        duration -= 1;

        int moveIndex = random.nextInt(4);
        var movement = Movement.defaults.get(moveIndex);

        return new PositionUpdate(unit, unit.getPosition().move(movement));
    }

    @Override
    public @NotNull Strategy nextStrategy(UnitView unit) {
        if (duration < 1) {
            return underlying.nextStrategy(unit);
        }
        return this;
    }
}
