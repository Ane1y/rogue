package ru.itmo.rogue.model.unit.strategy;
import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.PositionUpdate;

import java.util.ArrayList;
import java.util.List;


/**
 * Strategy of the aggressive unit,
 * Follows player, for next step chooses between ont of the neighbouring steps accessible for walking
 */
public class AgressiveStrategy implements Strategy  {

    //Unit always is enemy
    @Override
    public @NotNull StateUpdate getAction(UnitView unit, StateView state) {
        Position unitPos = unit.getPosition();
        Position playerPos = state.getPlayer().getPosition();
        var positions = Movement.defaults.stream()
                .map(unitPos::move)
                .filter(position -> state.getMap().isFloor(position))
                .toList();

        List<Double> distances = positions.stream()
                .map(p -> p.distance(playerPos))
                .toList();

        int minAt = 0;
        for (int i = 0; i < distances.size(); i++) {
            minAt = distances.get(i) < distances.get(minAt) ? i : minAt;
        }

        return new PositionUpdate(unit, positions.get(minAt));

    }
}
