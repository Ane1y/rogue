package ru.itmo.rogue.model.unit.strategy;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.unit.UnitFactory;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.state.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayerInputStrategy {

    @Test
    void queuedActions() {
        var player = UnitFactory.getPlayerUnit();
        assertEquals(PlayerInputStrategy.class, player.getStrategy().getClass());
        var strategy = (PlayerInputStrategy)player.getStrategy();

        var state = new State();

        List<Signal> signals = List.of(
                Signal.UP,
                Signal.SELECT,
                Signal.DOWN,
                Signal.RIGHT,
                Signal.BACK,
                Signal.LEFT,
                Signal.RIGHT
        );

        Position position = player.getPosition();
        List<Movement> movements = List.of(
                Movement.UP,    // UP
                Movement.NONE,  // SELECT
                Movement.DOWN,  // DOWN
                Movement.RIGHT, // RIGHT
                Movement.NONE,  // BACK
                Movement.LEFT,  // LEFT
                Movement.RIGHT  // RIGHT
        );

        // Enqueue
        for (var signal : signals) {
            strategy.setMovement(signal);
        }

        // Check
        for (var movement : movements) {
            var action = player.getAction(state);
            assertEquals(position.move(movement), action.dest());
        }
    }

}
