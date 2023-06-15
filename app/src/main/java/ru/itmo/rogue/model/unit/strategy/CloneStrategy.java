package ru.itmo.rogue.model.unit.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.NoUpdate;
import ru.itmo.rogue.model.updates.unit.CloneUnit;
import ru.itmo.rogue.model.updates.StateUpdate;

import java.util.Random;


/**
 * Cloning strategy, only creates clones does not attack
 */
public class CloneStrategy implements Strategy {

    private final Random random = new Random();
    @Override
    public @NotNull StateUpdate getAction(UnitView unit, StateView state) {
        if (random.nextInt(10) > 1) { // Has 50/50 probability to spawn clone
            return new NoUpdate();
        }

        int movementIndex = random.nextInt(4);
        var movement = Movement.defaults.get(movementIndex);
        var position = unit.getPosition().move(movement);
        return new CloneUnit(unit, position, true);
    }
}
