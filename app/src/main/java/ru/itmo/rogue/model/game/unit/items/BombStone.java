package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitUpdate;

public class BombStone implements Item {
    private final String name = "Bomb";

    int change = -1;

    public BombStone(){};
    public BombStone(int change) {
        this.change = change;
    }

    @Override
    public Delta apply(Unit unit, State state) {
        Delta delta = new Delta();
        for (Unit enemy : state.getUnits()) {
            enemy.changeHealth(change);
            delta.add(new UnitUpdate(enemy));
        }
        return delta;
    }

    @Override
    public String getName() {
        return name;
    }
}
