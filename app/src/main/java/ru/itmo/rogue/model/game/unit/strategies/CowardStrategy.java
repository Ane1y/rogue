package ru.itmo.rogue.model.game.unit.strategies;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Strategy;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

import java.util.ArrayList;
import java.util.List;

public class CowardStrategy implements Strategy {
    @Override
    public Action getAction(Unit unit, State state) {
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
        return new Action(possiblePos.get(minAt));
    }
}
