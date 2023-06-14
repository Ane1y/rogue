package ru.itmo.rogue.model.unit.strategy;
import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.PositionUpdate;

import java.util.ArrayList;
import java.util.List;

public class AgressiveStrategy implements Strategy  {

    //Unit always is enemy
    @Override
    public @NotNull StateUpdate getAction(UnitView unit, StateView state) {
        Position unitPos = unit.getPosition();
        Position playerPos = state.getPlayer().getPosition();
        List<Position> possiblePos = new ArrayList<>();
        possiblePos.add(new Position(unitPos.getX() + 1, unitPos.getY()));
        possiblePos.add(new Position(unitPos.getX() - 1, unitPos.getY()));
        possiblePos.add(new Position(unitPos.getX(), unitPos.getY() + 1));
        possiblePos.add(new Position(unitPos.getX(), unitPos.getY() - 1));

        List<Double> distances = possiblePos.stream().map(p -> p.distance(playerPos)).toList();
        int minAt = 0;

        for (int i = 0; i < distances.size(); i++) {
            minAt = distances.get(i) < distances.get(minAt) ? i : minAt;
        }

        return new PositionUpdate(unit, possiblePos.get(minAt));

    }
}
