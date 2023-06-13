package ru.itmo.rogue.model.game.unit.strategies;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Movement;
import ru.itmo.rogue.model.game.unit.Strategy;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

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
    public Action getAction(Unit unit, State state) {
        duration -= 1;

        var action = underlying.getAction(unit, state);
        var invert = random.nextBoolean();

        if (invert) {
            return invertAction(unit, action);
        }

        return turnAction(unit, action);
    }

    @Override
    public Strategy nextStrategy(Unit unit) {
        if (duration < 1) {
            return underlying.nextStrategy(unit);
        }
        return this;
    }

    private Action turnAction(Unit unit, Action action) {
        var movement = unit.getPosition().getMovement(action.dest());
        var rotated = new Movement(-movement.y(), movement.x());
        return new Action(unit.getPosition().move(rotated));
    }

    private Action invertAction(Unit unit, Action action) {
        var movement = unit.getPosition().getMovement(action.dest());
        var inverted = new Movement(-movement.x(), -movement.y());
        return new Action(unit.getPosition().move(inverted));
    }
}
